# 通过WebSocket推送协议解析结果实现计划

## 一、需求分析

用户希望通过WebSocket（STOMP协议）实时向前端推送协议解析结果。具体要求：

1. 使用 STOMP 协议替代简单 WebSocket，提升性能和兼容性
2. 创建统一的 WebSocket 配置类，支持端点注册和消息代理配置
3. 在 codec 包下的所有编解码类中，解析协议数据后通过 WebSocket 推送结果
4. 使用 topic 模式推送，主题格式：`/topic/antenna/status/{port}`
5. 支持 SockJS 回退机制，增强浏览器兼容性

## 二、当前状态分析

### 现有代码结构

```
ruoyi-xkd/protocol/codec/
├── AntennaAckCodec.java          # ACK响应编解码
├── AntennaFrameCodec.java        # 协议帧编解码
├── AntennaParamCodec.java        # 参数编解码
├── ByteCodec.java                # 字节工具
└── EphemerisPayloadCodec.java    # 星历载荷编解码

ruoyi-xkd/config/
└── WebSocketStompConfig.java     # 现有WebSocket配置（功能不完整）
```

### 现有 WebSocket 配置（不完整）

- 仅注册了 `/ws-stomp` 端点
- 仅启用了 `/topic` 消息代理
- 缺少 `/user` 单点推送支持
- 缺少应用前缀配置

## 三、实现步骤

### 步骤 1：修改 WebSocket 配置类

**文件**: `ruoyi-xkd/src/main/java/com/ruoyi/xkd/websocket/WebSocketStompConfig.java`

**修改内容**:
```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册 WebSocket 握手端点
        registry.addEndpoint("/ws-stomp")
                .setAllowedOriginPatterns("*")  // 允许所有来源（生产环境应配置具体域名）
                .withSockJS();  // 开启 SockJS 回退机制
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 启用简单消息代理，支持广播和单点推送
        registry.enableSimpleBroker("/topic", "/user");
        
        // 客户端发送消息的前缀
        registry.setApplicationDestinationPrefixes("/app");
        
        // 用户目的地前缀（用于点对点推送）
        registry.setUserDestinationPrefix("/user");
    }
}
```

### 步骤 2：创建协议解析结果 DTO

**新文件**: `ruoyi-xkd/src/main/java/com/ruoyi/xkd/domain/dto/ProtocolParseResult.java`

**字段设计**:
```java
@Data
public class ProtocolParseResult {
    private String cmdCode;           // 命令码（如 0x83）
    private String cmdName;           // 命令名称（如 CMD_QUERY_ACK）
    private String deviceCode;        // 设备编号
    private String direction;         // 方向（SEND/RECEIVE）
    private String remoteIp;          // 远端IP
    private Integer remotePort;       // 远端端口
    private Map<String, Object> params;  // 解析后的参数键值对
    private Date parseTime;           // 解析时间
    private String checkStatus;       // 校验状态（OK/FAILED）
}
```

### 步骤 3：修改 AntennaFrameCodec

**文件**: `ruoyi-xkd/src/main/java/com/ruoyi/xkd/protocol/codec/AntennaFrameCodec.java`

**修改内容**:
- 注入 `SimpMessagingTemplate`
- 在 `decode()` 方法中，解析成功后构建 `ProtocolParseResult`
- 通过 `messagingTemplate.convertAndSend("/topic/antenna/parse", result)` 推送

### 步骤 4：修改 AntennaParamCodec

**文件**: `ruoyi-xkd/src/main/java/com/ruoyi/xkd/protocol/codec/AntennaParamCodec.java`

**修改内容**:
- 在各个 `decode*Param()` 方法中，解析参数后构建参数结果对象
- 推送解析结果到 `/topic/antenna/params/{key}`

### 步骤 5：修改 AntennaAckCodec

**文件**: `ruoyi-xkd/src/main/java/com/ruoyi/xkd/protocol/codec/AntennaAckCodec.java`

**修改内容**:
- 在 `decodeSetAck()` 方法中，解析 ACK 后推送结果
- 主题：`/topic/antenna/ack`

### 步骤 6：修改 EphemerisPayloadCodec

**文件**: `ruoyi-xkd/src/main/java/com/ruoyi/xkd/protocol/codec/EphemerisPayloadCodec.java`

**修改内容**:
- 在星历数据解析后推送结果
- 主题：`/topic/antenna/ephemeris`

### 步骤 7：修改前端订阅逻辑

**文件**: `ruoyi-ui/src/api/websocket.js`

**修改内容**:
```javascript
// 订阅协议解析结果
stompClient.subscribe('/topic/antenna/parse', function (message) {
  const parseResult = JSON.parse(message.body)
  // 处理解析结果
  if (onParseResultCallback) {
    onParseResultCallback(parseResult)
  }
})

// 订阅参数解析结果
stompClient.subscribe('/topic/antenna/params', function (message) {
  const params = JSON.parse(message.body)
  if (onParamCallback) {
    onParamCallback(params)
  }
})
```

### 步骤 8：更新前端页面

**文件**: `ruoyi-ui/src/views/xkd/deviceStatus/index.vue`

**修改内容**:
- 在 `connectWebSocket` 中添加协议解析结果的消息处理
- 实时显示协议解析详情

## 四、推送主题设计

| 主题 | 用途 | 推送时机 |
|------|------|---------|
| `/topic/antenna/status` | 设备状态更新 | 查询响应/主动上报解析后 |
| `/topic/antenna/parse` | 协议帧解析结果 | 协议帧解码成功后 |
| `/topic/antenna/params` | 参数解析结果 | 参数解析成功后 |
| `/topic/antenna/ack` | ACK响应结果 | ACK解析成功后 |
| `/topic/antenna/ephemeris` | 星历数据解析结果 | 星历数据解析成功后 |

## 五、依赖检查

需要确保以下依赖已配置：

1. **Spring WebSocket 依赖**（已在 ruoyi-xkd/pom.xml 中配置）
2. **Lombok 依赖**（已添加）
3. **FastJSON 依赖**（已在父 pom.xml 中配置）

## 六、网关配置更新

**文件**: `ruoyi-gateway/src/main/resources/application.yml`

当前配置已包含：
```yaml
- id: ruoyi-xkd-ws
  uri: lb:ws://ruoyi-xkd
  predicates:
    - Path=/ws-stomp/**
```

需确保鉴权白名单包含 `/ws-stomp/**`（已配置）

## 七、风险与注意事项

| 风险项 | 应对措施 |
|--------|---------|
| 消息推送过于频繁 | 添加消息去重或节流机制 |
| 前端订阅过多导致性能问题 | 使用动态订阅/取消订阅 |
| 内存泄漏（STOMP 连接未关闭） | 确保页面销毁时断开连接 |
| 生产环境跨域问题 | 配置具体的 allowedOriginPatterns |
| 网关 WebSocket 转发异常 | 测试网关配置的正确性 |

## 八、测试验证步骤

1. 编译并重启 ruoyi-xkd 服务
2. 刷新前端页面，打开浏览器控制台查看 WebSocket 连接日志
3. 使用 `send_antenna_test.py` 发送测试数据
4. 验证前端控制台输出协议解析结果
5. 验证设备状态页面实时更新

## 九、预期效果

- 前端实时接收协议解析结果
- 可展示详细的协议解析过程（命令码、参数、校验状态等）
- 提升系统可观测性和调试能力
- 为后续协议分析和故障排查提供数据支持