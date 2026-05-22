import request from '@/utils/request'

// 查询任务计划列表
export function listTask(query) {
  return request({
    url: '/xkd/task/list',
    method: 'get',
    params: query
  })
}

// 查询任务计划详细
export function getTask(taskId) {
  return request({
    url: '/xkd/task/' + taskId,
    method: 'get'
  })
}

// 新增任务计划
export function addTask(data) {
  return request({
    url: '/xkd/task',
    method: 'post',
    data: data
  })
}

// 修改任务计划
export function updateTask(data) {
  return request({
    url: '/xkd/task',
    method: 'put',
    data: data
  })
}

// 删除任务计划
export function delTask(taskId) {
  return request({
    url: '/xkd/task/' + taskId,
    method: 'delete'
  })
}

// 启动定时任务
export function startQueryTask() {
  return request({
    url: '/xkd/task/start',
    method: 'get'
  })
}

// 停止定时任务
export function stopQueryTask() {
  return request({
    url: '/xkd/task/stop',
    method: 'get'
  })
}

// 查询定时任务状态
export function getQueryTaskStatus() {
  return request({
    url: '/xkd/task/status',
    method: 'get'
  })
}
