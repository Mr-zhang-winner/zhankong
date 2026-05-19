import request from '@/utils/request'

// 查询天线协议收发日志列表
export function listTAntennaProtocolLog(query) {
  return request({
    url: '/xkd/TAntennaProtocolLog/list',
    method: 'get',
    params: query
  })
}

// 查询天线协议收发日志详细
export function getTAntennaProtocolLog(logId) {
  return request({
    url: '/xkd/TAntennaProtocolLog/' + logId,
    method: 'get'
  })
}

// 新增天线协议收发日志
export function addTAntennaProtocolLog(data) {
  return request({
    url: '/xkd/TAntennaProtocolLog',
    method: 'post',
    data: data
  })
}

// 修改天线协议收发日志
export function updateTAntennaProtocolLog(data) {
  return request({
    url: '/xkd/TAntennaProtocolLog',
    method: 'put',
    data: data
  })
}

// 删除天线协议收发日志
export function delTAntennaProtocolLog(logId) {
  return request({
    url: '/xkd/TAntennaProtocolLog/' + logId,
    method: 'delete'
  })
}
