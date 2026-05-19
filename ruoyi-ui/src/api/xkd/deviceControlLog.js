import request from '@/utils/request'

// 查询设备控制日志列表
export function listDeviceControlLog(query) {
  return request({
    url: '/xkd/deviceControlLog/list',
    method: 'get',
    params: query
  })
}

// 查询设备控制日志详细
export function getDeviceControlLog(controlId) {
  return request({
    url: '/xkd/deviceControlLog/' + controlId,
    method: 'get'
  })
}

// 新增设备控制日志
export function addDeviceControlLog(data) {
  return request({
    url: '/xkd/deviceControlLog',
    method: 'post',
    data: data
  })
}

// 修改设备控制日志
export function updateDeviceControlLog(data) {
  return request({
    url: '/xkd/deviceControlLog',
    method: 'put',
    data: data
  })
}

// 删除设备控制日志
export function delDeviceControlLog(controlId) {
  return request({
    url: '/xkd/deviceControlLog/' + controlId,
    method: 'delete'
  })
}
