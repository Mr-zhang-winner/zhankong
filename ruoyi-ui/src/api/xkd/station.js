import request from '@/utils/request'

// 查询站址配置列表
export function listStation(query) {
  return request({
    url: '/xkd/station/list',
    method: 'get',
    params: query
  })
}

// 查询站址配置详细
export function getStation(stationId) {
  return request({
    url: '/xkd/station/' + stationId,
    method: 'get'
  })
}

// 新增站址配置
export function addStation(data) {
  return request({
    url: '/xkd/station',
    method: 'post',
    data: data
  })
}

// 修改站址配置
export function updateStation(data) {
  return request({
    url: '/xkd/station',
    method: 'put',
    data: data
  })
}

// 删除站址配置
export function delStation(stationId) {
  return request({
    url: '/xkd/station/' + stationId,
    method: 'delete'
  })
}
