import socket
import sys

# 协议常量
FRAME_FLAG = 0x7E
ESCAPE = 0x7D
ESCAPE_7E = 0x5E
ESCAPE_7D = 0x5D

# 命令字
CMD_QUERY_ACK = 0x83
CMD_REPORT = 0x84
CMD_FAC_QUERY_ACK = 0x87

# 参数KEY
KEY_SEND_FREQ = 0x02
KEY_RECV_FREQ = 0x04
KEY_BUC_DECAY = 0x06
KEY_BDC_DECAY = 0x07
KEY_ANT_SWITCH = 0x08
KEY_ANT_STATUS = 0x0A
KEY_ANT_RSRP = 0x0C
KEY_ANT_ANGLE = 0x0D
KEY_ANT_PAR_STATUS = 0x0E
KEY_SAT_NO = 0x13
KEY_ANT_TEMP = 0x14
KEY_LOCK_STATUS = 0x35
KEY_DEVICE_NAME = 0x6E
KEY_DEVICE_OEM = 0x6F
KEY_DEVICE_SN = 0x70
KEY_DEVICE_DATE = 0x71
KEY_SOFT_VERSION = 0x72

KEY_SELF_CHECK_READY = 0x81
KEY_REPORT_TOD = 0x82
KEY_PARABOLA_REACH = 0x83

# 网络配置
DEVICE_IP = "127.0.0.1"
DEVICE_PORT = 8100
HOST_IP = "127.0.0.1"
HOST_PORT = 8500


def u16(v):
    """无符号16位整数转字节（大端序）"""
    v = v & 0xFFFF
    return bytes([(v >> 8) & 0xFF, v & 0xFF])


def s16(v):
    """有符号16位整数转字节（大端序）"""
    if v < 0:
        v = 0x10000 + v
    return bytes([(v >> 8) & 0xFF, v & 0xFF])


def u32(v):
    """无符号32位整数转字节（大端序）"""
    v = v & 0xFFFFFFFF
    return bytes([
        (v >> 24) & 0xFF,
        (v >> 16) & 0xFF,
        (v >> 8) & 0xFF,
        v & 0xFF
    ])


def tlv(key, value):
    """TLV编码"""
    return bytes([key & 0xFF, len(value) & 0xFF]) + value


def escape_body(data):
    """转义处理"""
    out = bytearray()
    for b in data:
        if b == FRAME_FLAG:
            out.extend([ESCAPE, ESCAPE_7E])
        elif b == ESCAPE:
            out.extend([ESCAPE, ESCAPE_7D])
        else:
            out.append(b)
    return bytes(out)


def encode_frame(cmd, payload):
    """编码协议帧"""
    length = 1 + len(payload)
    checksum = (cmd + sum(payload)) & 0xFF
    
    body = bytes([
        (length >> 8) & 0xFF,
        length & 0xFF,
        cmd & 0xFF
    ]) + payload + bytes([checksum])
    
    return bytes([FRAME_FLAG]) + escape_body(body) + bytes([FRAME_FLAG])


def hexstr(data):
    """字节转十六进制字符串"""
    return data.hex(" ").upper()


def build_query_ack_normal():
    """构建正常查询响应"""
    payload = b""
    
    # 0x0D 方位角/俯仰角，单位 /100
    payload += tlv(KEY_ANT_ANGLE, u16(12050) + u16(3520))
    
    # 0x0A 天线状态，0 表示正常
    payload += tlv(KEY_ANT_STATUS, bytes([0x00]))
    
    # 0x0E 参数状态
    payload += tlv(KEY_ANT_PAR_STATUS, bytes([0x01]))
    
    # 0x14 温度，25.30 -> 2530
    payload += tlv(KEY_ANT_TEMP, u16(2530))
    
    # 0x0C RSRP，-72（有符号16位）
    payload += tlv(KEY_ANT_RSRP, s16(-72))
    
    # 0x13 卫星号
    payload += tlv(KEY_SAT_NO, u16(3))
    
    # 0x02 发射频率
    payload += tlv(KEY_SEND_FREQ, u32(14500000))
    
    # 0x04 接收频率
    payload += tlv(KEY_RECV_FREQ, u32(12500000))
    
    # 0x06 BUC衰减 20.00 -> 2000
    payload += tlv(KEY_BUC_DECAY, u16(2000))
    
    # 0x07 BDC衰减 15.00 -> 1500
    payload += tlv(KEY_BDC_DECAY, u16(1500))
    
    # 0x08 天线开关
    payload += tlv(KEY_ANT_SWITCH, bytes([0x15]))
    
    # 0x35 锁定状态
    payload += tlv(KEY_LOCK_STATUS, bytes([0x01]))
    
    return encode_frame(CMD_QUERY_ACK, payload)


def build_query_ack_abnormal():
    """构建异常查询响应（告警状态非0）"""
    payload = b""
    
    payload += tlv(KEY_ANT_ANGLE, u16(12050) + u16(3520))
    
    # 告警状态非0，测试写告警日志
    payload += tlv(KEY_ANT_STATUS, bytes([0x01]))
    
    payload += tlv(KEY_ANT_TEMP, u16(2530))
    
    return encode_frame(CMD_QUERY_ACK, payload)


def build_report_normal():
    """构建正常主动上报"""
    payload = b""
    
    # 0x81 自检就绪，0表示成功
    payload += tlv(KEY_SELF_CHECK_READY, bytes([0x00]))
    
    # 0x82 TOD时间
    payload += tlv(KEY_REPORT_TOD, bytes([0x12, 0x34, 0x56, 0x78]))
    
    # 0x83 抛物面达到起跟位置
    payload += tlv(KEY_PARABOLA_REACH, bytes([0x01]))
    
    return encode_frame(CMD_REPORT, payload)


def build_report_abnormal():
    """构建异常主动上报"""
    payload = b""
    
    # 0x81 自检失败
    payload += tlv(KEY_SELF_CHECK_READY, bytes([0x02]))
    
    return encode_frame(CMD_REPORT, payload)


def build_factory_ack():
    """构建出厂信息响应"""
    payload = b""
    
    # 0x6E 设备名称
    payload += tlv(KEY_DEVICE_NAME, b"ANT001         ")
    
    # 0x6F 厂商信息
    payload += tlv(KEY_DEVICE_OEM, b"XianDong Tech  Co.,Ltd.")
    
    # 0x70 序列号
    payload += tlv(KEY_DEVICE_SN, b"202401010000001")
    
    # 0x71 生产日期
    payload += tlv(KEY_DEVICE_DATE, b"2024-01-01 00:00")
    
    # 0x72 软件版本
    payload += tlv(KEY_SOFT_VERSION, b"V1.0.0         ")
    
    return encode_frame(CMD_FAC_QUERY_ACK, payload)


def send_single(frame, description):
    """发送单帧数据"""
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.bind((DEVICE_IP, DEVICE_PORT))
    
    print(f"\n{description}")
    print(f"发送数据: {hexstr(frame)}")
    
    sock.sendto(frame, (HOST_IP, HOST_PORT))
    sock.close()
    
    print("发送成功")


def send_with_ack(frame, description):
    """发送数据并等待ACK"""
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.bind((DEVICE_IP, DEVICE_PORT))
    sock.settimeout(3)
    
    print(f"\n{description}")
    print(f"发送数据: {hexstr(frame)}")
    
    sock.sendto(frame, (HOST_IP, HOST_PORT))
    
    try:
        data, addr = sock.recvfrom(2048)
        print(f"收到ACK来自 {addr}: {hexstr(data)}")
    except socket.timeout:
        print("超时未收到ACK")
    
    sock.close()


def send_multiple(count=5, interval=1):
    """连续发送多帧数据"""
    import time
    
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.bind((DEVICE_IP, DEVICE_PORT))
    
    print(f"\n连续发送 {count} 帧查询响应，间隔 {interval} 秒")
    
    for i in range(count):
        frame = build_query_ack_normal()
        print(f"\n第 {i+1} 帧:")
        print(f"发送数据: {hexstr(frame)}")
        sock.sendto(frame, (HOST_IP, HOST_PORT))
        time.sleep(interval)
    
    sock.close()
    print("\n发送完成")


def main():
    if len(sys.argv) < 2:
        print("用法: python3 send_antenna_test.py <模式>")
        print("模式列表:")
        print("  query-normal    - 发送正常查询响应")
        print("  query-abnormal  - 发送异常查询响应")
        print("  report-normal   - 发送正常主动上报")
        print("  report-abnormal - 发送异常主动上报")
        print("  factory-ack     - 发送出厂信息响应")
        print("  multiple        - 连续发送5帧查询响应")
        sys.exit(1)
    
    mode = sys.argv[1]
    
    if mode == "query-normal":
        send_single(build_query_ack_normal(), "发送 CMD_QUERY_ACK (正常)")
    elif mode == "query-abnormal":
        send_single(build_query_ack_abnormal(), "发送 CMD_QUERY_ACK (异常)")
    elif mode == "report-normal":
        send_with_ack(build_report_normal(), "发送 CMD_REPORT (正常)")
    elif mode == "report-abnormal":
        send_with_ack(build_report_abnormal(), "发送 CMD_REPORT (异常)")
    elif mode == "factory-ack":
        send_single(build_factory_ack(), "发送 CMD_FAC_QUERY_ACK")
    elif mode == "multiple":
        send_multiple()
    else:
        print(f"未知模式: {mode}")
        sys.exit(1)


if __name__ == "__main__":
    main()
