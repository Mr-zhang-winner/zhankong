# WebSocket 实时推送实现计划（基于 STOMP 协议）

## 一、需求背景
当前系统收到天线数据后只存入数据库，前端需要手动刷新才能看到数据变化。需要实现：
1. 后端在收到天线数据并存储数据库后，通过 WebSocket (STOMP) 推送数据到前端
2. 前端建立 WebSocket 连接，实时接收数据更新

## 二、仓库研究结论
1. ✅ pom.xml 中已包含 `spring-boot-starter-websocket` 依赖
2. ❌ 目前没有任何 WebSocket 相关的实现代码
3. ✅ 天线数据处理流程清晰：
   - 查询响应：`AntennaQueryAckService.handleQueryAck()` → 存入数据库
   - 主动上报：`AntennaReportService.handleReport()` → 存入数据库
4. ✅ 前端设备状态页面：`ruoyi-ui/src/views/xkd/deviceStatus/index.vue`

## 三、实现方案（基于 STOMP 协议）

### 3.1 后端实现
#### 新增文件
| 文件路径 | 功能说明 |
|---------|---------|
| `ruoyi-modules/ruoyi-xkd/src/main/java/com/ruoyi/xkd/websocket/WebSocketStompConfig.java` | WebSocket STOMP 配置类 |
| `ruoyi-modules/ruoyi-xkd/src/main/java/com/ruoyi/xkd/domain/dto/DeviceStatusPushDTO.java` | 设备状态推送 DTO |
| `ruoyi-modules/ruoyi-xkd/src/main/java/com/ruoyi/xkd/domain/dto/WebSocketMessage.java` | WebSocket 通用消息包装类 |

#### 修改文件
| 文件路径 | 修改内容 |
|---------|---------|
| `AntennaQueryAckService.java` | 存储数据库后，通过 SimpMessagingTemplate 推送设备状态 |
| `AntennaReportService.java` | 存储数据库后，通过 SimpMessagingTemplate 推送设备状态 |

### 3.2 前端实现
#### 新增文件
| 文件路径 | 功能说明 |
|---------|---------|
| `ruoyi-ui/src/api/websocket.js` | WebSocket 连接管理 API |

#### 修改文件
| 文件路径 | 修改内容 |
|---------|---------|
| `ruoyi-ui/src/views/xkd/deviceStatus/index.vue` | 添加 STOMP 客户端连接，实时接收并更新数据 |
| `ruoyi-ui/package.json` | 添加 sockjs-client 和 stompjs 依赖（如果没有） |

## 四、实现步骤

### 阶段一：后端 WebSocket STOMP 基础框架
1. 创建 `WebSocketMessage` - WebSocket 通用消息包装类
2. 创建 `DeviceStatusPushDTO` - 设备状态推送对象
3. 创建 `WebSocketStompConfig` - WebSocket STOMP 配置类
   - 启用 STOMP 消息代理
   - 配置端点和消息前缀

### 阶段二：集成推送逻辑
1. 修改 `AntennaQueryAckService.handleQueryAck()`
   - 注入 `SimpMessagingTemplate`
   - 数据库插入成功后，构建消息并推送到 `/topic/antenna/status`
2. 修改 `AntennaReportService.handleReport()`
   - 注入 `SimpMessagingTemplate`
   - 数据库插入成功后，构建消息并推送到 `/topic/antenna/status`

### 阶段三：前端 STOMP 客户端集成
1. 检查并安装 sockjs-client 和 stompjs 依赖
2. 创建 `websocket.js` - 封装 STOMP 连接管理
3. 修改 `deviceStatus/index.vue`
   - 在 created/mounted 中建立连接
   - 订阅 `/topic/antenna/status` 主题
   - 接收推送消息并更新页面数据
   - 在 beforeDestroy 中断开连接

## 五、技术细节

### 5.1 推送消息格式
```json
{
  "type": "DEVICE_STATUS_UPDATE",
  "timestamp": "2024-01-01T12:00:00.000Z",
  "data": {
    "statusId": 123,
    "deviceId": 1,
    "deviceCode": "ANT001",
    "deviceName": "模拟天线ANT001",
    "azimuth": 45.5,
    "elevation": 30.2,
    "runStatus": "NORMAL",
    "workParams": {...},
    "collectTime": "2024-01-01 12:00:00"
  }
}
```

### 5.2 STOMP 配置
- **WebSocket 端点**: `ws://host:port/ws-stomp`
- **SockJS 回退**: `http://host:port/ws-stomp`
- **消息前缀**: `/app`
- **发布订阅主题**: `/topic/antenna/status`

### 5.3 安全性考虑
- 当前不做用户认证（简化实现）
- 所有连接的前端都能收到推送
- 后续可添加认证和权限控制

## 六、依赖与风险

| 依赖项 | 说明 |
|-------|------|
| spring-boot-starter-websocket | ✅ 已添加 |
| sockjs-client | 前端依赖，需要安装 |
| stompjs | 前端依赖，需要安装 |

| 风险点 | 应对措施 |
|-------|---------|
| 前端与后端连接断开 | 前端实现自动重连机制 |
| 消息丢失 | 每次更新都推最新的完整状态 |
| 资源泄露 | 在组件销毁时关闭连接 |
| 版本兼容性 | 使用稳定版本的 sockjs-client 和 stompjs |

## 七、修改文件清单

### 新增文件
- `ruoyi-modules/ruoyi-xkd/src/main/java/com/ruoyi/xkd/domain/dto/WebSocketMessage.java`
- `ruoyi-modules/ruoyi-xkd/src/main/java/com/ruoyi/xkd/domain/dto/DeviceStatusPushDTO.java`
- `ruoyi-modules/ruoyi-xkd/src/main/java/com/ruoyi/xkd/websocket/WebSocketStompConfig.java`
- `ruoyi-ui/src/api/websocket.js`

### 修改文件
- `ruoyi-modules/ruoyi-xkd/src/main/java/com/ruoyi/xkd/protocol/service/AntennaQueryAckService.java`
- `ruoyi-modules/ruoyi-xkd/src/main/java/com/ruoyi/xkd/protocol/service/AntennaReportService.java`
- `ruoyi-ui/src/views/xkd/deviceStatus/index.vue`
- `ruoyi-ui/package.json`（如果需要添加依赖）
