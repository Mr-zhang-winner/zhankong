-- 任务类型
INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '任务类型', 'xkd_task_type', '0', 'admin', NOW(), 'XKD任务类型'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'xkd_task_type');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 1, '手动任务', 'MANUAL', 'xkd_task_type', '', 'primary', 'Y', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_task_type' AND dict_value = 'MANUAL');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 2, '自动任务', 'AUTO', 'xkd_task_type', '', 'success', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_task_type' AND dict_value = 'AUTO');


-- 任务执行状态
INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '任务执行状态', 'xkd_task_execute_status', '0', 'admin', NOW(), 'XKD任务执行状态'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'xkd_task_execute_status');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 1, '待执行', 'WAITING', 'xkd_task_execute_status', '', 'info', 'Y', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_task_execute_status' AND dict_value = 'WAITING');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 2, '执行中', 'RUNNING', 'xkd_task_execute_status', '', 'warning', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_task_execute_status' AND dict_value = 'RUNNING');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 3, '已完成', 'FINISHED', 'xkd_task_execute_status', '', 'success', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_task_execute_status' AND dict_value = 'FINISHED');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 4, '已取消', 'CANCELLED', 'xkd_task_execute_status', '', 'danger', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_task_execute_status' AND dict_value = 'CANCELLED');


-- 任务冲突状态
INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '任务冲突状态', 'xkd_task_conflict_status', '0', 'admin', NOW(), 'XKD任务冲突状态'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'xkd_task_conflict_status');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 1, '无冲突', '0', 'xkd_task_conflict_status', '', 'success', 'Y', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_task_conflict_status' AND dict_value = '0');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 2, '有冲突', '1', 'xkd_task_conflict_status', '', 'danger', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_task_conflict_status' AND dict_value = '1');

