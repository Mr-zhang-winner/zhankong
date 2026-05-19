
# 协议结构体集成实现计划

## 一、需求分析

当前协议服务层使用简化的 `AntennaParam`（key-len-value）模型进行协议编解码。需要将其改造为使用已定义的结构体模型，包括：
- 联合体（U8Union、U16Union、S16Union、U32Union、S32Union、DoubleUnion）
- 参数类型结构体（AntTxFreqParam、AntRxFreqParam、AntBucDecayParam等）
- 复合参数结构体（QueryParam、SetParam）

## 二、改造目标

1. **编码器改造**：修改 `AntennaParamCodec` 支持结构体编解码
2. **命令构建服务改造**：使用结构体构建协议命令
3. **响应处理服务改造**：使用结构体解析响应数据
4. **保持向后兼容**：原有API保持不变

## 三、文件修改清单

| 文件路径 | 修改内容 |
|---------|---------|
| `protocol/codec/AntennaParamCodec.java` | 添加结构体编码方法 |
| `protocol/service/AntennaCommandBuildService.java` | 使用结构体构建命令 |
| `protocol/service/AntennaQueryAckService.java` | 使用结构体解析查询响应 |
| `protocol/service/AntennaReportService.java` | 使用结构体解析上报数据 |
| `protocol/service/AntennaFactoryAckService.java` | 使用FactoryParam解析出厂信息 |
| `protocol/service/AntennaEphemerisAckService.java` | 使用EphesParam解析星历数据 |

## 四、详细步骤

### 步骤1：改造 AntennaParamCodec

添加针对各个参数类型结构体的编解码方法：

```java
// 编码方法
byte[] encodeAntModeParam(AntModeParam param)
byte[] encodeAntTxFreqParam(AntTxFreqParam param)
byte[] encodeAntRxFreqParam(AntRxFreqParam param)
byte[] encodeAntBucDecayParam(AntBucDecayParam param)
byte[] encodeAntSwStateParam(AntSwStateParam param)
// ... 其他参数类型

// 解码方法
AntModeParam decodeAntModeParam(byte[] data, int offset)
AntTxFreqParam decodeAntTxFreqParam(byte[] data, int offset)
// ... 其他参数类型

// 复合结构体编解码
byte[] encodeQueryParam(QueryParam param)
QueryParam decodeQueryParam(byte[] payload)
byte[] encodeSetParam(SetParam param)
SetParam decodeSetParam(byte[] payload)
```

### 步骤2：改造 AntennaCommandBuildService

将原有使用 `ByteCodec` 的方式改为使用结构体：

```java
// 改造前
public byte[] buildSetTxFreqPayload(long freqMhz) {
    return singleParam(KEY_SEND_FREQ, ByteCodec.u32(freqMhz));
}

// 改造后
public byte[] buildSetTxFreqPayload(long freqHz) {
    AntTxFreqParam param = new AntTxFreqParam();
    param.setKey(KEY_SEND_FREQ);
    param.getAntTxFreq().setA(freqHz);
    return paramCodec.encodeAntTxFreqParam(param);
}
```

### 步骤3：改造 AntennaQueryAckService

使用 QueryParam 解析响应：

```java
// 改造前
List<AntennaParam> params = paramCodec.decodeParams(frame.getPayload());
for (AntennaParam param : params) {
    int key = param.getKey();
    byte[] value = param.getValue();
    // 手动解析字节数组...
}

// 改造后
QueryParam params = paramCodec.decodeQueryParam(frame.getPayload());
long txFreq = params.getAntTxFreqT().getAntTxFreq().getA();
int temperature = params.getAntTempT().getAntTemp().getA();
```

### 步骤4：改造 AntennaFactoryAckService

使用 FactoryParam 解析出厂信息：

```java
public void handleFactoryAck(AntennaFrame frame) {
    FactoryParam factory = paramCodec.decodeFactoryParam(frame.getPayload());
    String deviceName = factory.getStrDeviceName();
    String deviceSn = factory.getStrDeviceSn();
    // 保存到数据库...
}
```

### 步骤5：改造 AntennaEphemerisAckService

使用 EphesParam 解析星历数据：

```java
public void handleEphemerisAck(AntennaFrame frame) {
    EphesParam ephes = paramCodec.decodeEphesParam(frame.getPayload());
    int satId = ephes.getSatId().getA();
    U32Union[] gpsWeek = ephes.getGpsWeek();
    // 处理星历数据...
}
```

## 五、依赖与风险

### 依赖检查

1. 所有结构体类已存在于 `protocol/model` 和 `protocol/model/param` 目录
2. 联合体类已存在于 `protocol/model/union` 目录
3. 无需新增依赖

### 风险评估

| 风险 | 描述 | 应对策略 |
|------|------|----------|
| 字节序问题 | 结构体使用大端序，需确保与设备一致 | 在联合体重确认字节序转换逻辑 |
| 长度不匹配 | 结构体字段长度与协议不一致 | 严格按照C语言结构体定义实现 |
| 测试覆盖 | 改造后需要测试各命令 | 编写单元测试覆盖主要场景 |
| 数据类型溢出 | 无符号整数处理不当 | 使用 & 0xFF/0xFFFF 等掩码确保正确 |

## 六、测试验证

改造完成后需进行以下测试：

1. **编码测试**：验证各参数类型编码后的字节数组正确性
2. **解码测试**：验证从字节数组解码到结构体的正确性
3. **集成测试**：验证完整的命令发送和响应接收流程
4. **边界测试**：验证最大值、最小值、负数等边界情况

## 七、预期成果

完成改造后，协议服务层将：
1. 使用强类型结构体进行数据处理
2. 代码可读性和可维护性提升
3. 减少手动字节操作错误
4. 与C语言协议定义保持一致
