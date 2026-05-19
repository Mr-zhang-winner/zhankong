# 实现天线协议结构体

## 一、任务目标

将 C 代码中的协议结构体完整移植到 Java 项目中，完善协议数据模型。

## 二、需要实现的结构体

### 1. 联合体（Union）类 - 字节序转换工具

| C Union | Java 类 | 说明 |
|---------|---------|------|
| `U8_UNION` | `U8Union` | 1字节联合体 |
| `U16_UNION` | `U16Union` | 2字节无符号整数 |
| `S16_UNION` | `S16Union` | 2字节有符号整数 |
| `U32_UNION` | `U32Union` | 4字节无符号整数 |
| `S32_UNION` | `S32Union` | 4字节有符号整数 |
| `DOUBLE_UNION` | `DoubleUnion` | 8字节双精度浮点数 |

### 2. 基础参数结构体

| C Struct | Java 类 | 说明 |
|----------|---------|------|
| `FACTORY_PARAM` | `FactoryParam` | 出厂参数（设备名、SN、版本等） |
| `CONTROLLER_PARAM` | `ControllerParam` | 控制器参数 |
| `GPSTOD_PARAM` | `GpsTodParam` | GPS 时间参数 |
| `INS_PARAM` | `InsParam` | 惯导参数 |
| `EPHES_PARAM` | `EphesParam` | 星历参数 |

### 3. 位域结构体

| C Struct | Java 类 | 说明 |
|----------|---------|------|
| `BIT_SW` | `BitSw` | 天线开关状态位域 |

### 4. 参数类型结构体（带 key + len 头）

| C Struct | Java 类 | 说明 |
|----------|---------|------|
| `ANT_MODE_T` | `AntModeParam` | 天线模式参数 |
| `ANT_TX_FREQ_T` | `AntTxFreqParam` | 发射频率参数 |
| `ANT_RX_FREQ_T` | `AntRxFreqParam` | 接收频率参数 |
| `ANT_MFTX_FREQ_T` | `AntMfTxFreqParam` | 中频发射频率参数 |
| `ANT_MFRX_FREQ_T` | `AntMfRxFreqParam` | 中频接收频率参数 |
| `ANT_SW_STATE_T` | `AntSwStateParam` | 开关状态参数 |
| `ANT_TX_POL_T` | `AntTxPolParam` | 发射极化参数 |
| `ANT_RX_POL_T` | `AntRxPolParam` | 接收极化参数 |
| `ANT_BUC_DECAY_T` | `AntBucDecayParam` | BUC 衰减参数 |
| `ANT_BDC_DECAY_T` | `AntBdcDecayParam` | BDC 衰减参数 |
| `ANT_SAT_ID_T` | `AntSatIdParam` | 卫星 ID 参数 |
| `ANT_TEMP_T` | `AntTempParam` | 温度参数 |
| `ANT_ALARM_T` | `AntAlarmParam` | 告警参数 |
| `ANT_ATTITUDE_T` | `AntAttitudeParam` | 姿态参数（方位角+俯仰角） |
| `ANT_PSRP_T` | `AntPsrpParam` | 信号强度参数 |
| `ANT_CAP_T` | `AntCapParam` | 能力参数 |
| `ANT_CAP_GAIN_T` | `AntCapGainParam` | 能力增益参数 |
| `CHANGE_SAT_T` | `ChangeSatParam` | 换星参数 |

### 5. 复合参数结构体

| C Struct | Java 类 | 说明 |
|----------|---------|------|
| `QUERY_PARAM` | `QueryParam` | 查询参数集合 |
| `SET_PARAM` | `SetParam` | 设置参数集合 |

## 三、实现步骤

### 第一步：创建包结构
```
com.ruoyi.xkd.protocol.model
├── union/          # 联合体工具类
├── param/          # 参数结构体
└── factory/        # 出厂参数等复合结构体
```

### 第二步：实现联合体工具类

1. 创建 `U8Union.java`
2. 创建 `U16Union.java`
3. 创建 `S16Union.java`
4. 创建 `U32Union.java`
5. 创建 `S32Union.java`
6. 创建 `DoubleUnion.java`

### 第三步：实现位域结构体

1. 创建 `BitSw.java` - 天线开关状态位域

### 第四步：实现基础参数结构体

1. 创建 `FactoryParam.java` - 出厂参数
2. 创建 `ControllerParam.java` - 控制器参数
3. 创建 `GpsTodParam.java` - GPS 时间参数
4. 创建 `InsParam.java` - 惯导参数
5. 创建 `EphesParam.java` - 星历参数

### 第五步：实现参数类型结构体

1. 创建 `AntModeParam.java`
2. 创建 `AntTxFreqParam.java`
3. 创建 `AntRxFreqParam.java`
4. 创建 `AntMfTxFreqParam.java`
5. 创建 `AntMfRxFreqParam.java`
6. 创建 `AntSwStateParam.java`
7. 创建 `AntTxPolParam.java`
8. 创建 `AntRxPolParam.java`
9. 创建 `AntBucDecayParam.java`
10. 创建 `AntBdcDecayParam.java`
11. 创建 `AntSatIdParam.java`
12. 创建 `AntTempParam.java`
13. 创建 `AntAlarmParam.java`
14. 创建 `AntAttitudeParam.java`
15. 创建 `AntPsrpParam.java`
16. 创建 `AntCapParam.java`
17. 创建 `AntCapGainParam.java`
18. 创建 `ChangeSatParam.java`

### 第六步：实现复合参数结构体

1. 创建 `QueryParam.java` - 查询参数集合
2. 创建 `SetParam.java` - 设置参数集合

### 第七步：编译验证

运行 `mvn clean compile -pl ruoyi-modules/ruoyi-xkd -am` 验证代码无编译错误。

## 四、文件清单

```
protocol/model/
├── union/
│   ├── U8Union.java
│   ├── U16Union.java
│   ├── S16Union.java
│   ├── U32Union.java
│   ├── S32Union.java
│   └── DoubleUnion.java
├── BitSw.java
├── FactoryParam.java
├── ControllerParam.java
├── GpsTodParam.java
├── InsParam.java
├── EphesParam.java
├── AntModeParam.java
├── AntTxFreqParam.java
├── AntRxFreqParam.java
├── AntMfTxFreqParam.java
├── AntMfRxFreqParam.java
├── AntSwStateParam.java
├── AntTxPolParam.java
├── AntRxPolParam.java
├── AntBucDecayParam.java
├── AntBdcDecayParam.java
├── AntSatIdParam.java
├── AntTempParam.java
├── AntAlarmParam.java
├── AntAttitudeParam.java
├── AntPsrpParam.java
├── AntCapParam.java
├── AntCapGainParam.java
├── ChangeSatParam.java
├── QueryParam.java
└── SetParam.java
```

## 五、注意事项

1. **字节序**：所有多字节数据采用大端序（Big Endian），与 C 代码保持一致
2. **位域处理**：Java 没有位域语法，使用 int/long 的位操作模拟
3. **压缩结构**：`__attribute__((packed))` 在 Java 中不需要，Java 对象有自己的内存布局
4. **类型对应**：
   - `u8` → `int` 或 `byte`
   - `u16` → `int`
   - `s16` → `int`
   - `u32` → `long`
   - `s32` → `int`
   - `double` → `double`
