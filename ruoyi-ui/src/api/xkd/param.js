import request from '@/utils/request'

// 查询系统参数列表
export function listParam(query) {
  return request({
    url: '/xkd/param/list',
    method: 'get',
    params: query
  })
}

// 查询系统参数详细
export function getParam(paramId) {
  return request({
    url: '/xkd/param/' + paramId,
    method: 'get'
  })
}

// 新增系统参数
export function addParam(data) {
  return request({
    url: '/xkd/param',
    method: 'post',
    data: data
  })
}

// 修改系统参数
export function updateParam(data) {
  return request({
    url: '/xkd/param',
    method: 'put',
    data: data
  })
}

// 删除系统参数
export function delParam(paramId) {
  return request({
    url: '/xkd/param/' + paramId,
    method: 'delete'
  })
}
