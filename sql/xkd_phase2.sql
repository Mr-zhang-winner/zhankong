DROP TABLE IF EXISTS t_alarm_log;
DROP TABLE IF EXISTS t_device_status;
DROP TABLE IF EXISTS t_device_config;
DROP TABLE IF EXISTS t_param_change_log;
DROP TABLE IF EXISTS t_system_param;
DROP TABLE IF EXISTS t_satellite_config;
DROP TABLE IF EXISTS t_station_config;

SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS t_station_config (
    station_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '站址ID',
    station_name VARCHAR(100) NOT NULL COMMENT '站址名称',
    longitude DECIMAL(10,6) NOT NULL COMMENT '经度',
    latitude DECIMAL(10,6) NOT NULL COMMENT '纬度',
    address VARCHAR(255) DEFAULT NULL COMMENT '地址',
    contact VARCHAR(100) DEFAULT NULL COMMENT '联系方式',
    status CHAR(1) DEFAULT '0' COMMENT '状态：0启用 1禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (station_id)
) COMMENT='站址配置表';

CREATE TABLE IF NOT EXISTS t_satellite_config (
    satellite_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '卫星ID',
    satellite_code VARCHAR(64) NOT NULL COMMENT '卫星编号',
    satellite_name VARCHAR(100) NOT NULL COMMENT '卫星名称',
    ephemeris_type VARCHAR(20) NOT NULL COMMENT '星历类型：TLE/PVT/PARAM12',
    orbit_params TEXT COMMENT '轨道参数JSON',
    receive_params TEXT COMMENT '接收参数JSON',
    status CHAR(1) DEFAULT '0' COMMENT '状态：0启用 1禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (satellite_id),
    UNIQUE KEY uk_satellite_code (satellite_code)
) COMMENT='卫星配置表';

CREATE TABLE IF NOT EXISTS t_system_param (
    param_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '参数ID',
    param_code VARCHAR(100) NOT NULL COMMENT '参数编码',
    param_name VARCHAR(100) NOT NULL COMMENT '参数名称',
    param_value VARCHAR(500) NOT NULL COMMENT '参数值',
    param_type VARCHAR(50) NOT NULL COMMENT '参数类型：SYSTEM/DEVICE/SATELLITE/ALARM/STORAGE/TASK',
    param_desc VARCHAR(500) DEFAULT NULL COMMENT '参数描述',
    status CHAR(1) DEFAULT '0' COMMENT '状态：0启用 1禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (param_id),
    UNIQUE KEY uk_param_code (param_code)
) COMMENT='系统参数表';

CREATE TABLE IF NOT EXISTS t_param_change_log (
    log_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    param_id BIGINT NOT NULL COMMENT '参数ID',
    param_code VARCHAR(100) NOT NULL COMMENT '参数编码',
    old_value VARCHAR(500) DEFAULT NULL COMMENT '旧值',
    new_value VARCHAR(500) NOT NULL COMMENT '新值',
    change_by VARCHAR(64) DEFAULT '' COMMENT '修改人',
    change_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    change_reason VARCHAR(500) DEFAULT NULL COMMENT '修改原因',
    PRIMARY KEY (log_id)
) COMMENT='参数变更记录表';

CREATE TABLE IF NOT EXISTS t_device_config (
    device_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '设备ID',
    device_code VARCHAR(64) NOT NULL COMMENT '设备编号',
    device_name VARCHAR(100) NOT NULL COMMENT '设备名称',
    device_type VARCHAR(50) NOT NULL COMMENT '设备类型',
    station_id BIGINT DEFAULT NULL COMMENT '所属站址ID',
    ip_address VARCHAR(64) DEFAULT NULL COMMENT '设备IP',
    udp_port INT DEFAULT NULL COMMENT 'UDP端口',
    config_params TEXT COMMENT '设备配置参数JSON',
    status CHAR(1) DEFAULT '0' COMMENT '状态：0启用 1禁用',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建者',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新者',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (device_id),
    UNIQUE KEY uk_device_code (device_code)
) COMMENT='设备配置表';

CREATE TABLE IF NOT EXISTS t_device_status (
    status_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '状态ID',
    device_id BIGINT NOT NULL COMMENT '设备ID',
    device_code VARCHAR(64) NOT NULL COMMENT '设备编号',
    device_name VARCHAR(100) DEFAULT NULL COMMENT '设备名称',
    azimuth DECIMAL(10,4) DEFAULT NULL COMMENT '方位角',
    elevation DECIMAL(10,4) DEFAULT NULL COMMENT '俯仰角',
    voltage DECIMAL(10,4) DEFAULT NULL COMMENT '工作电压',
    work_params TEXT COMMENT '工作参数JSON',
    run_status VARCHAR(20) NOT NULL COMMENT '运行状态：NORMAL/ABNORMAL/OFFLINE',
    collect_time DATETIME DEFAULT NULL COMMENT '采集时间',
    report_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '上报时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (status_id),
    KEY idx_device_id (device_id),
    KEY idx_report_time (report_time)
) COMMENT='设备状态表';

CREATE TABLE IF NOT EXISTS t_alarm_log (
    alarm_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '告警ID',
    alarm_type VARCHAR(50) NOT NULL COMMENT '告警类型：DEVICE/TASK/SYSTEM/NETWORK/ILLEGAL',
    alarm_level VARCHAR(20) NOT NULL COMMENT '告警级别：INFO/WARN/ERROR/CRITICAL',
    alarm_desc VARCHAR(500) NOT NULL COMMENT '告警描述',
    alarm_location VARCHAR(200) DEFAULT NULL COMMENT '告警位置',
    related_id VARCHAR(64) DEFAULT NULL COMMENT '关联ID：设备ID/任务ID等',
    occur_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发生时间',
    handle_status CHAR(1) DEFAULT '0' COMMENT '处理状态：0未处理 1已确认 2已处理 3已归档',
    handler VARCHAR(64) DEFAULT NULL COMMENT '处理人',
    handle_time DATETIME DEFAULT NULL COMMENT '处理时间',
    handle_result VARCHAR(500) DEFAULT NULL COMMENT '处理结果',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (alarm_id),
    KEY idx_alarm_type (alarm_type),
    KEY idx_alarm_level (alarm_level),
    KEY idx_occur_time (occur_time),
    KEY idx_handle_status (handle_status)
) COMMENT='告警日志表';


-- 星历类型
INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '星历类型', 'xkd_ephemeris_type', '0', 'admin', NOW(), 'XKD星历类型'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'xkd_ephemeris_type');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 1, 'TLE', 'TLE', 'xkd_ephemeris_type', '', 'primary', 'Y', '0', 'admin', NOW(), 'TLE两行根数'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_ephemeris_type' AND dict_value = 'TLE');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 2, 'PVT', 'PVT', 'xkd_ephemeris_type', '', 'success', 'N', '0', 'admin', NOW(), '位置速度时间参数'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_ephemeris_type' AND dict_value = 'PVT');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 3, '12参数', 'PARAM12', 'xkd_ephemeris_type', '', 'warning', 'N', '0', 'admin', NOW(), '12参数星历'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_ephemeris_type' AND dict_value = 'PARAM12');


-- 系统参数类型
INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '系统参数类型', 'xkd_param_type', '0', 'admin', NOW(), 'XKD系统参数类型'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'xkd_param_type');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 1, '系统参数', 'SYSTEM', 'xkd_param_type', '', 'primary', 'Y', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_param_type' AND dict_value = 'SYSTEM');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 2, '设备参数', 'DEVICE', 'xkd_param_type', '', 'success', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_param_type' AND dict_value = 'DEVICE');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 3, '卫星参数', 'SATELLITE', 'xkd_param_type', '', 'info', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_param_type' AND dict_value = 'SATELLITE');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 4, '告警参数', 'ALARM', 'xkd_param_type', '', 'warning', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_param_type' AND dict_value = 'ALARM');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 5, '存储参数', 'STORAGE', 'xkd_param_type', '', 'info', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_param_type' AND dict_value = 'STORAGE');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 6, '任务参数', 'TASK', 'xkd_param_type', '', 'danger', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_param_type' AND dict_value = 'TASK');


-- 设备类型
INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '设备类型', 'xkd_device_type', '0', 'admin', NOW(), 'XKD设备类型'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'xkd_device_type');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 1, '天线', 'ANTENNA', 'xkd_device_type', '', 'primary', 'Y', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_device_type' AND dict_value = 'ANTENNA');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 2, '接收机', 'RECEIVER', 'xkd_device_type', '', 'success', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_device_type' AND dict_value = 'RECEIVER');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 3, '控制器', 'CONTROLLER', 'xkd_device_type', '', 'warning', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_device_type' AND dict_value = 'CONTROLLER');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 4, '其他', 'OTHER', 'xkd_device_type', '', 'info', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_device_type' AND dict_value = 'OTHER');


-- 设备运行状态
INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '设备运行状态', 'xkd_run_status', '0', 'admin', NOW(), 'XKD设备运行状态'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'xkd_run_status');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 1, '正常', 'NORMAL', 'xkd_run_status', '', 'success', 'Y', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_run_status' AND dict_value = 'NORMAL');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 2, '异常', 'ABNORMAL', 'xkd_run_status', '', 'danger', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_run_status' AND dict_value = 'ABNORMAL');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 3, '离线', 'OFFLINE', 'xkd_run_status', '', 'info', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_run_status' AND dict_value = 'OFFLINE');


-- 告警类型
INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '告警类型', 'xkd_alarm_type', '0', 'admin', NOW(), 'XKD告警类型'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'xkd_alarm_type');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 1, '设备告警', 'DEVICE', 'xkd_alarm_type', '', 'danger', 'Y', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_alarm_type' AND dict_value = 'DEVICE');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 2, '任务告警', 'TASK', 'xkd_alarm_type', '', 'warning', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_alarm_type' AND dict_value = 'TASK');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 3, '系统告警', 'SYSTEM', 'xkd_alarm_type', '', 'primary', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_alarm_type' AND dict_value = 'SYSTEM');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 4, '网络告警', 'NETWORK', 'xkd_alarm_type', '', 'info', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_alarm_type' AND dict_value = 'NETWORK');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 5, '非法操作', 'ILLEGAL', 'xkd_alarm_type', '', 'danger', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_alarm_type' AND dict_value = 'ILLEGAL');


-- 告警级别
INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '告警级别', 'xkd_alarm_level', '0', 'admin', NOW(), 'XKD告警级别'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'xkd_alarm_level');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 1, '提示', 'INFO', 'xkd_alarm_level', '', 'info', 'Y', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_alarm_level' AND dict_value = 'INFO');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 2, '警告', 'WARN', 'xkd_alarm_level', '', 'warning', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_alarm_level' AND dict_value = 'WARN');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 3, '错误', 'ERROR', 'xkd_alarm_level', '', 'danger', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_alarm_level' AND dict_value = 'ERROR');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 4, '严重', 'CRITICAL', 'xkd_alarm_level', '', 'danger', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_alarm_level' AND dict_value = 'CRITICAL');


-- 告警处理状态
INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '告警处理状态', 'xkd_handle_status', '0', 'admin', NOW(), 'XKD告警处理状态'
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'xkd_handle_status');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 1, '未处理', '0', 'xkd_handle_status', '', 'danger', 'Y', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_handle_status' AND dict_value = '0');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 2, '已确认', '1', 'xkd_handle_status', '', 'warning', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_handle_status' AND dict_value = '1');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 3, '已处理', '2', 'xkd_handle_status', '', 'success', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_handle_status' AND dict_value = '2');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
SELECT 4, '已归档', '3', 'xkd_handle_status', '', 'info', 'N', '0', 'admin', NOW(), ''
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'xkd_handle_status' AND dict_value = '3');