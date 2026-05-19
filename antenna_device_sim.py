import socket
import time

# 协议常量
FRAME_FLAG = 0x7E
ESCAPE = 0x7D
ESCAPE_7E = 0x5E
ESCAPE_7D = 0x5D

# 命令字
CMD_SET = 0x80
CMD_SET_ACK = 0x81
CMD_QUERY = 0x82
CMD_QUERY_ACK = 0x83
CMD_REPORT = 0x84
CMD_REPORT_ACK = 0x85
CMD_FAC_QUERY = 0x86
CMD_FAC_QUERY_ACK = 0x87

# ACK状态码
PARAM_SET_OK = 0x20
PARAM_SET_FAILED = 0x21
PARAM_FRAME_ERROR = 0x29

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

# 网络配置
DEVICE_IP = "127.0.0.1"
DEVICE_PORT = 8100
HOST_IP = "127.0.0.1"
HOST_PORT = 8500

# 模拟设备状态
simulated_state = {
    'send_freq': 14500000,
    'recv_freq': 12500000,
    'buc_decay': 2000,
    'bdc_decay': 1500,
    'ant_switch': 0x15,
    'ant_status': 0x00,
    'rsrp': -72,
    'azimuth': 12050,
    'elevation': 3520,
    'par_status': 0x01,
    'sat_no': 3,
    'temp': 2530,
    'lock_status': 0x01,
    'device_name': b'ANT001         ',
    'device_oem': b'XianDong Tech  Co.,Ltd.',
    'device_sn': b'202401010000001',
    'device_date': b'2024-01-01 00:00',
    'soft_version': b'V1.0.0         '
}


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


def unescape_body(data):
    """反转义处理"""
    out = bytearray()
    i = 0
    while i < len(data):
        b = data[i]
        if b == ESCAPE:
            if i + 1 >= len(data):
                raise ValueError("转义字节不完整")
            nxt = data[i + 1]
            if nxt == ESCAPE_7E:
                out.append(FRAME_FLAG)
            elif nxt == ESCAPE_7D:
                out.append(ESCAPE)
            else:
                raise ValueError(f"非法转义序列: 7D {nxt:02X}")
            i += 2
        else:
            out.append(b)
            i += 1
    return bytes(out)


def checksum(cmd, payload):
    """计算校验和"""
    return (cmd + sum(payload)) & 0xFF


def encode_frame(cmd, payload):
    """编码协议帧"""
    length = 1 + len(payload)
    check = checksum(cmd, payload)
    body = bytes([
        (length >> 8) & 0xFF,
        length & 0xFF,
        cmd & 0xFF
    ]) + payload + bytes([check])
    return bytes([FRAME_FLAG]) + escape_body(body) + bytes([FRAME_FLAG])


def decode_frame(frame):
    """解码协议帧"""
    if len(frame) < 6:
        raise ValueError("协议帧长度不足")
    if frame[0] != FRAME_FLAG or frame[-1] != FRAME_FLAG:
        raise ValueError("协议帧起止符错误")
    
    body = unescape_body(frame[1:-1])
    if len(body) < 4:
        raise ValueError("协议帧body长度不足")
    
    length = (body[0] << 8) | body[1]
    cmd = body[2]
    payload_len = length - 1
    payload_start = 3
    payload_end = payload_start + payload_len
    
    if payload_end >= len(body):
        raise ValueError("payload长度越界")
    
    payload = body[payload_start:payload_end]
    actual_check = body[payload_end]
    expected_check = checksum(cmd, payload)
    
    if actual_check != expected_check:
        raise ValueError(f"校验失败 expected={expected_check:02X}, actual={actual_check:02X}")
    
    return cmd, payload


def parse_tlv(payload):
    """解析TLV格式payload"""
    params = []
    i = 0
    while i + 2 <= len(payload):
        key = payload[i]
        length = payload[i + 1]
        if i + 2 + length > len(payload):
            raise ValueError(f"TLV长度越界 key=0x{key:02X}, len={length}")
        value = payload[i + 2:i + 2 + length]
        params.append((key, length, value))
        i += 2 + length
    return params


def build_query_ack_payload():
    """构建查询响应payload"""
    payload = b""
    payload += tlv(KEY_ANT_ANGLE, u16(simulated_state['azimuth']) + u16(simulated_state['elevation']))
    payload += tlv(KEY_ANT_STATUS, bytes([simulated_state['ant_status']]))
    payload += tlv(KEY_ANT_PAR_STATUS, bytes([simulated_state['par_status']]))
    payload += tlv(KEY_ANT_TEMP, u16(simulated_state['temp']))
    payload += tlv(KEY_ANT_RSRP, s16(simulated_state['rsrp']))
    payload += tlv(KEY_SAT_NO, u16(simulated_state['sat_no']))
    payload += tlv(KEY_SEND_FREQ, u32(simulated_state['send_freq']))
    payload += tlv(KEY_RECV_FREQ, u32(simulated_state['recv_freq']))
    payload += tlv(KEY_BUC_DECAY, u16(simulated_state['buc_decay']))
    payload += tlv(KEY_BDC_DECAY, u16(simulated_state['bdc_decay']))
    payload += tlv(KEY_ANT_SWITCH, bytes([simulated_state['ant_switch']]))
    payload += tlv(KEY_LOCK_STATUS, bytes([simulated_state['lock_status']]))
    return payload


def build_factory_ack_payload():
    """构建出厂信息响应payload"""
    payload = b""
    payload += tlv(KEY_DEVICE_NAME, simulated_state['device_name'])
    payload += tlv(KEY_DEVICE_OEM, simulated_state['device_oem'])
    payload += tlv(KEY_DEVICE_SN, simulated_state['device_sn'])
    payload += tlv(KEY_DEVICE_DATE, simulated_state['device_date'])
    payload += tlv(KEY_SOFT_VERSION, simulated_state['soft_version'])
    return payload


def build_set_ack_payload(params):
    """构建设置响应payload"""
    payload = bytearray()
    for key, length, value in params:
        print(f"  设置参数 key=0x{key:02X}, value={value.hex(' ')}")
        
        # 更新模拟状态
        if key == KEY_SEND_FREQ:
            simulated_state['send_freq'] = int.from_bytes(value, 'big')
        elif key == KEY_RECV_FREQ:
            simulated_state['recv_freq'] = int.from_bytes(value, 'big')
        elif key == KEY_BUC_DECAY:
            simulated_state['buc_decay'] = int.from_bytes(value, 'big')
        elif key == KEY_BDC_DECAY:
            simulated_state['bdc_decay'] = int.from_bytes(value, 'big')
        elif key == KEY_ANT_SWITCH:
            simulated_state['ant_switch'] = value[0]
        elif key == KEY_SAT_NO:
            simulated_state['sat_no'] = int.from_bytes(value, 'big')
        
        # 返回成功
        payload.append(key)
        payload.append(PARAM_SET_OK)
    
    return bytes(payload)


def hexstr(data):
    """字节转十六进制字符串"""
    return data.hex(" ").upper()


def main():
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    sock.bind((DEVICE_IP, DEVICE_PORT))
    
    print(f"模拟天线设备已启动")
    print(f"监听地址: {DEVICE_IP}:{DEVICE_PORT}")
    print(f"发送地址: {HOST_IP}:{HOST_PORT}")
    print("支持命令: CMD_SET(0x80), CMD_QUERY(0x82), CMD_FAC_QUERY(0x86)")
    print("按 Ctrl+C 退出")
    
    while True:
        try:
            data, addr = sock.recvfrom(4096)
            print(f"\n[{time.strftime('%H:%M:%S')}] 收到来自 {addr} 的数据:")
            print(f"原始数据: {hexstr(data)}")
            
            try:
                cmd, payload = decode_frame(data)
                print(f"解析成功: cmd=0x{cmd:02X}, payload={hexstr(payload)}")
                
                if cmd == CMD_SET:
                    print("处理 CMD_SET (参数设置)")
                    params = parse_tlv(payload)
                    ack_payload = build_set_ack_payload(params)
                    ack_frame = encode_frame(CMD_SET_ACK, ack_payload)
                    sock.sendto(ack_frame, (HOST_IP, HOST_PORT))
                    print(f"回复 CMD_SET_ACK: {hexstr(ack_frame)}")
                    
                elif cmd == CMD_QUERY:
                    print("处理 CMD_QUERY (全查询)")
                    ack_payload = build_query_ack_payload()
                    ack_frame = encode_frame(CMD_QUERY_ACK, ack_payload)
                    sock.sendto(ack_frame, (HOST_IP, HOST_PORT))
                    print(f"回复 CMD_QUERY_ACK: {hexstr(ack_frame)}")
                    
                elif cmd == CMD_FAC_QUERY:
                    print("处理 CMD_FAC_QUERY (出厂信息查询)")
                    ack_payload = build_factory_ack_payload()
                    ack_frame = encode_frame(CMD_FAC_QUERY_ACK, ack_payload)
                    sock.sendto(ack_frame, (HOST_IP, HOST_PORT))
                    print(f"回复 CMD_FAC_QUERY_ACK: {hexstr(ack_frame)}")
                    
                else:
                    print(f"未处理的命令: 0x{cmd:02X}")
                
            except Exception as e:
                print(f"解析失败: {e}")
                ack_payload = bytes([0x00, PARAM_FRAME_ERROR])
                ack_frame = encode_frame(CMD_SET_ACK, ack_payload)
                sock.sendto(ack_frame, (HOST_IP, HOST_PORT))
                print(f"回复帧错误 ACK: {hexstr(ack_frame)}")
                
        except KeyboardInterrupt:
            print("\n模拟天线设备已关闭")
            break
        except Exception as e:
            print(f"错误: {e}")


if __name__ == "__main__":
    main()
