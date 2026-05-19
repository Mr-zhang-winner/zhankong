import request from '@/utils/request'

// 查询告警日志列表
export function listAlarm(query) {
  return request({
    url: '/xkd/alarm/list',
    method: 'get',
    params: query
  })
}

// 查询告警日志详细
export function getAlarm(alarmId) {
  return request({
    url: '/xkd/alarm/' + alarmId,
    method: 'get'
  })
}

// 新增告警日志
export function addAlarm(data) {
  return request({
    url: '/xkd/alarm',
    method: 'post',
    data: data
  })
}

// 修改告警日志
export function updateAlarm(data) {
  return request({
    url: '/xkd/alarm',
    method: 'put',
    data: data
  })
}

// 删除告警日志
export function delAlarm(alarmId) {
  return request({
    url: '/xkd/alarm/' + alarmId,
    method: 'delete'
  })
}
