import SockJS from 'sockjs-client'
import Stomp from 'stompjs'

let stompClient = null
let reconnectAttempts = 0
const MAX_RECONNECT_ATTEMPTS = 5
const RECONNECT_DELAY = 3000

export function connectWebSocket(onMessageCallback) {
  const socket = new SockJS(process.env.VUE_APP_BASE_API + '/ws-stomp')
  stompClient = Stomp.over(socket)
  stompClient.debug = null

  stompClient.connect(
    {},
    function (frame) {
      console.log('WebSocket connected: ' + frame)
      reconnectAttempts = 0
      stompClient.subscribe('/topic/antenna/status', function (message) {
        if (onMessageCallback && message.body) {
          onMessageCallback(JSON.parse(message.body))
        }
      })
    },
    function (error) {
      console.error('WebSocket connection error:', error)
      attemptReconnect(onMessageCallback)
    }
  )
}

function attemptReconnect(onMessageCallback) {
  if (reconnectAttempts < MAX_RECONNECT_ATTEMPTS) {
    reconnectAttempts++
    console.log(`WebSocket reconnecting... Attempt ${reconnectAttempts}/${MAX_RECONNECT_ATTEMPTS}`)
    setTimeout(() => {
      connectWebSocket(onMessageCallback)
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
