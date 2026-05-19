import request from '@/utils/request'

// 查询卫星配置列表
export function listStaellite(query) {
  return request({
    url: '/xkd/staellite/list',
    method: 'get',
    params: query
  })
}

// 查询卫星配置详细
export function getStaellite(satelliteId) {
  return request({
    url: '/xkd/staellite/' + satelliteId,
    method: 'get'
  })
}

// 新增卫星配置
export function addStaellite(data) {
  return request({
    url: '/xkd/staellite',
    method: 'post',
    data: data
  })
}

// 修改卫星配置
export function updateStaellite(data) {
  return request({
    url: '/xkd/staellite',
    method: 'put',
    data: data
  })
}

// 删除卫星配置
export function delStaellite(satelliteId) {
  return request({
    url: '/xkd/staellite/' + satelliteId,
    method: 'delete'
  })
}
