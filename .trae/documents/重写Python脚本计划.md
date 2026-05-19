# 重写天线模拟脚本计划

## 一、需求分析

根据当前后端协议代码，需要重写两个Python脚本：

| 脚本 | 功能 |
|------|------|
| `antenna_device_sim.py` | 模拟天线设备，支持接收多种命令并回复 |
| `send_antenna_test.py` | 发送测试数据，支持多种命令类型测试 |

## 二、协议规范（根据后端代码）

### 帧结构
```
7E | 长度(2字节大端) | 命令字(1字节) | payload(N字节) | 校验和(1字节) | 7E
```

### 转义规则
- 0x7E → 0x7D 0x5E
- 0x7D → 0x7D 0x5D

### 校验和计算
```
checksum = (cmd + sum(payload)) & 0xFF
```

### 支持的命令

| 命令字 | 命令名 | 方向 |
|--------|--------|------|
| 0x80 | CMD_SET | 软件→天线 |
| 0x81 | CMD_SET_ACK | 天线→软件 |
| 0x82 | CMD_QUERY | 软件→天线 |
| 0x83 | CMD_QUERY_ACK | 天线→软件 |
| 0x84 | CMD_REPORT | 天线→软件 |
| 0x85 | CMD_REPORT_ACK | 软件→天线 |
| 0x86 | CMD_FAC_QUERY | 软件→天线 |
| 0x87 | CMD_FAC_QUERY_ACK | 天线→软件 |

### 参数 Key

| Key | 含义 | 长度 |
|-----|------|------|
| 0x02 | 发射频率 | 4 |
| 0x04 | 接收频率 | 4 |
| 0x06 | BUC衰减 | 2 |
| 0x07 | BDC衰减 | 2 |
| 0x08 | 天线开关 | 1 |
| 0x0A | 天线状态 | 1 |
| 0x0C | RSRP | 2 (有符号) |
| 0x0D | 天线角度 | 4 (2+2) |
| 0x0E | 参数状态 | 1 |
| 0x13 | 卫星号 | 2 |
| 0x14 | 温度 | 2 |
| 0x35 | 锁定状态 | 1 |
| 0x6E | 设备名称 | 16 |
| 0x6F | 厂商信息 | 24 |
| 0x70 | 序列号 | 15 |
| 0x71 | 生产日期 | 16 |
| 0x72 | 软件版本 | 16 |

## 三、修改计划

### 3.1 重写 antenna_device_sim.py

**功能增强：**
1. 支持 CMD_SET、CMD_QUERY、CMD_FAC_QUERY 命令
2. 正确回复 CMD_SET_ACK、CMD_QUERY_ACK、CMD_FAC_QUERY_ACK
3. 添加有符号整数转换函数
4. 修复协议解析逻辑

### 3.2 重写 send_antenna_test.py

**功能增强：**
1. 支持发送 CMD_QUERY_ACK、CMD_REPORT、CMD_FAC_QUERY_ACK
2. 添加有符号整数转换函数
3. 修复 RSRP 值编码错误
4. 添加更多测试模式

## 四、文件清单

### 修改文件
- `/home/zzz/RuoYi-Cloud/antenna_device_sim.py`
- `/home/zzz/RuoYi-Cloud/send_antenna_test.py`

## 五、技术细节

### 5.1 整数转换函数

```python
def u16(v):  # 无符号16位
def s16(v):  # 有符号16位
def u32(v):  # 无符号32位
def s32(v):  # 有符号32位
```

### 5.2 TLV编码

```python
def tlv(key, value):
    return bytes([key & 0xFF, len(value) & 0xFF]) + value
```

### 5.3 端口配置

| 端口 | 用途 |
|------|------|
| 8100 | 模拟天线监听端口 |
| 8500 | 后端服务监听端口 |

## 六、风险评估

| 风险 | 应对措施 |
|------|---------|
| 端口冲突 | 两个脚本不能同时运行 |
| 协议不兼容 | 严格按照后端代码实现 |
| 字节序错误 | 使用大端序 |

## 七、测试验证

1. 启动后端服务（端口8500）
2. 运行模拟天线脚本
3. 发送测试数据
4. 检查数据库是否有数据入库
5. 检查前端是否实时更新
