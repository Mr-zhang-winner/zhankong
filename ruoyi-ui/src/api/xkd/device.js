import request from '@/utils/request'

// 查询设备配置列表
export function listDevice(query) {
  return request({
    url: '/xkd/device/list',
    method: 'get',
    params: query
  })
}

// 查询设备配置详细
export function getDevice(deviceId) {
  return request({
    url: '/xkd/device/' + deviceId,
    method: 'get'
  })
}

// 新增设备配置
export function addDevice(data) {
  return request({
    url: '/xkd/device',
    method: 'post',
    data: data
  })
}

// 修改设备配置
export function updateDevice(data) {
  return request({
    url: '/xkd/device',
    method: 'put',
    data: data
  })
}

// 删除设备配置
export function delDevice(deviceId) {
  return request({
    url: '/xkd/device/' + deviceId,
    method: 'delete'
  })
}
