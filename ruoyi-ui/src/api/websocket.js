import SockJS from 'sockjs-client'
import Stomp from 'stompjs'

let stompClient = null
let reconnectAttempts = 0
const MAX_RECONNECT_ATTEMPTS = 5
const RECONNECT_DELAY = 3000

// 订阅回调
let onStatusCallback = null
let onParseCallback = null
let onAckCallback = null
let onFactoryCallback = null

export function connectWebSocket(callbacks) {
  const socket = new SockJS(process.env.VUE_APP_BASE_API + '/ws-stomp')
  stompClient = Stomp.over(socket)
  stompClient.debug = null

  // 兼容旧版调用方式
  if (typeof callbacks === 'function') {
    onStatusCallback = callbacks
  } else if (callbacks && typeof callbacks === 'object') {
    onStatusCallback = callbacks.onStatus
    onParseCallback = callbacks.onParse
    onAckCallback = callbacks.onAck
    onFactoryCallback = callbacks.onFactory
  }

  stompClient.connect(
    {},
    function (frame) {
      console.log('WebSocket connected: ' + frame)
      reconnectAttempts = 0

      // 订阅设备状态更新
      if (onStatusCallback) {
        stompClient.subscribe('/topic/antenna/status', function (message) {
          if (message.body) {
            onStatusCallback(JSON.parse(message.body))
          }
        })
      }

      // 订阅协议解析结果
      if (onParseCallback) {
        stompClient.subscribe('/topic/antenna/parse', function (message) {
          if (message.body) {
            onParseCallback(JSON.parse(message.body))
          }
        })
      }

      // 订阅ACK响应结果
      if (onAckCallback) {
        stompClient.subscribe('/topic/antenna/ack', function (message) {
          if (message.body) {
            onAckCallback(JSON.parse(message.body))
          }
        })
      }

      // 订阅出厂信息结果
      if (onFactoryCallback) {
        stompClient.subscribe('/topic/antenna/factory', function (message) {
          if (message.body) {
            onFactoryCallback(JSON.parse(message.body))
          }
        })
      }
    },
    function (error) {
      console.error('WebSocket connection error:', error)
      attemptReconnect(callbacks)
    }
  )
}

function attemptReconnect(callbacks) {
  if (reconnectAttempts < MAX_RECONNECT_ATTEMPTS) {
    reconnectAttempts++
    console.log(`WebSocket reconnecting... Attempt ${reconnectAttempts}/${MAX_RECONNECT_ATTEMPTS}`)
    setTimeout(() => {
      connectWebSocket(callbacks)
    }, RECONNECT_DELAY)
  } else {
    console.error('WebSocket max reconnect attempts reached')
  }
}

export function disconnectWebSocket() {
  if (stompClient && stompClient.connected) {
    stompClient.disconnect(function () {
      console.log('WebSocket disconnected')
    })
    stompClient = null
  }
}

export function isWebSocketConnected() {
  return stompClient && stompClient.connected
}