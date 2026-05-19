# 协议日志记录功能完善计划

## 目标
完善天线协议日志记录功能，确保所有协议命令的收发都有完整的日志记录。

## 当前状态分析

### 已实现功能
- `AntennaProtocolSendService.send()` 记录主动发送命令 SEND 日志
- `AntennaUdpServer.listen()` 记录成功解码的 RECEIVE 日志
- 覆盖 CMD_SET、CMD_QUERY、CMD_FAC_QUERY、CMD_BRD_SEARCH、CMD_EPHES_SET 的发送日志
- 覆盖 CMD_SET_ACK、CMD_QUERY_ACK、CMD_REPORT、CMD_FAC_QUERY_ACK、CMD_BRD_SEARCH_ACK、CMD_EPHES_SET_ACK 的接收日志
- `TAntennaProtocolLog` 已包含 `result_status` 字段
- SQL 表定义已包含 `result_status` 字段
- `ByteCodec.toHex()` 已支持多参数重载

### 需补充功能

#### 1. CMD_REPORT_ACK (0x85) SEND 日志
**问题**: CMD_REPORT_ACK 是在 `AntennaReportService.sendReportAck()` 中直接发送的，不经过 `AntennaProtocolSendService.send()`，因此没有 SEND 日志。

**解决方案**: 在 `AntennaReportService.sendReportAck()` 中添加协议日志记录逻辑。

#### 2. 解码失败 RECEIVE 日志
**问题**: 如果协议帧解码失败（起止符错误、长度错误、checksum 错误、转义错误），当前只在 catch 中记录错误日志，不会写入 `t_antenna_protocol_log`。

**解决方案**: 在 `AntennaUdpServer.listen()` 的 catch 块中添加解码失败日志记录。

#### 3. 业务处理结果状态记录
**问题**: RECEIVE 日志目前只记录了协议校验状态，但没有记录业务处理结果。

**解决方案**: 区分 `check_status`（协议校验结果）和 `result_status`（业务处理结果）。在 dispatch 成功/失败时更新 result_status。

## 实现步骤

### 步骤 1: 修改 AntennaReportService
**文件**: `ruoyi-modules/ruoyi-xkd/src/main/java/com/ruoyi/xkd/protocol/service/AntennaReportService.java`

**修改内容**:
1. 添加 `ITAntennaProtocolLogService` 依赖注入
2. 在 `sendReportAck()` 方法中添加日志记录调用
3. 新增 `writeReportAckProtocolLog()` 私有方法

**新增字段**:
```java
private final ITAntennaProtocolLogService antennaProtocolLogService;
```

**新增方法**:
```java
private void writeReportAckProtocolLog(AntennaFrame frame, int status, byte[] ackFrame, byte[] payload)
```

### 步骤 2: 修改 AntennaUdpServer
**文件**: `ruoyi-modules/ruoyi-xkd/src/main/java/com/ruoyi/xkd/udp/AntennaUdpServer.java`

**修改内容**:
1. 添加 `writeDecodeFailureProtocolLog()` 方法
2. 在 listen() 的 catch 块中调用该方法记录解码失败日志
3. 修改 `writeReceiveProtocolLog()` 方法，增加对 `result_status` 的支持

**新增方法**:
```java
private void writeDecodeFailureProtocolLog(DatagramPacket packet, Exception error)
```

**日志内容**:
- `direction = RECEIVE`
- `cmd_code = UNKNOWN`
- `cmd_name = UNKNOWN`
- `frame_hex = 原始UDP数据`
- `check_status = FAILED`
- `result_status = FAILED`
- `error_msg = 解码异常信息`

### 步骤 3: 更新现有 SEND 日志写入点
**文件**: `ruoyi-modules/ruoyi-xkd/src/main/java/com/ruoyi/xkd/protocol/service/AntennaProtocolSendService.java`

**修改内容**:
1. 在 `buildSendProtocolLog()` 中设置 `result_status = SUCCESS`
2. 确保所有 SEND 日志都有 `result_status` 字段

### 步骤 4: 验证日志覆盖完整性
验证以下命令的日志记录是否完整:

| 命令 | 方向 | 日志写入点 | 状态 |
|------|------|-----------|------|
| CMD_SET (0x80) | SEND | AntennaProtocolSendService.send() | ✅ |
| CMD_SET_ACK (0x81) | RECEIVE | AntennaUdpServer.listen() | ✅ |
| CMD_QUERY (0x82) | SEND | AntennaProtocolSendService.send() | ✅ |
| CMD_QUERY_ACK (0x83) | RECEIVE | AntennaUdpServer.listen() | ✅ |
| CMD_REPORT (0x84) | RECEIVE | AntennaUdpServer.listen() | ✅ |
| CMD_REPORT_ACK (0x85) | SEND | AntennaReportService.sendReportAck() | 🔧 需补充 |
| CMD_FAC_QUERY (0x86) | SEND | AntennaProtocolSendService.send() | ✅ |
| CMD_FAC_QUERY_ACK (0x87) | RECEIVE | AntennaUdpServer.listen() | ✅ |
| CMD_BRD_SEARCH (0x88) | SEND | AntennaProtocolSendService.broadcast() | ✅ |
| CMD_BRD_SEARCH_ACK (0x89) | RECEIVE | AntennaUdpServer.listen() | ✅ |
| CMD_EPHES_SET (0x8A) | SEND | AntennaProtocolSendService.send() | ✅ |
| CMD_EPHES_SET_ACK (0x8B) | RECEIVE | AntennaUdpServer.listen() | ✅ |
| 解码失败数据 | RECEIVE | AntennaUdpServer.listen() catch | 🔧 需补充 |

## 依赖注入
- `AntennaReportService` 需要注入 `ITAntennaProtocolLogService`
- `ByteCodec.toHex()` 需要支持多参数重载（offset, length）

## 风险评估
- 低：日志记录失败不应影响主要业务流程
- 所有新增的日志记录代码都应包含异常处理，确保不影响主流程
- 解码失败日志的记录可能影响性能（每帧失败都要写数据库），建议控制频率或使用异步写入

## 注意事项
1. 日志写入不应影响主业务流程，所有日志写入操作都应包含 try-catch
2. 日志记录应尽量精简，避免过多数据库操作影响性能
3. CMD_BRD_SEARCH 通过广播方式发送，也需要补充 SEND 日志记录
