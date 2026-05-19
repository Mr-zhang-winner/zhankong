# 任务计划冲突检测功能实现计划

## 需求分析

在任务计划管理模块中实现冲突检测功能，核心功能包括：

1. 检测同一站址或同一设备在时间段上的冲突
2. 在新增、修改任务时自动进行冲突检测
3. 对执行中或已完成的任务不允许修改和删除
4. 提供独立的冲突检测接口

## 实现方案

### 核心设计思路

1. **Mapper 层：查询冲突任务
2. **Service 层：冲突检测逻辑封装
3. **业务层**：CRUD 操作时调用检测
4. **控制层**：提供冲突检测接口

### 技术栈

- MyBatis 持久层
- Spring 服务层
- Spring Boot Web 控制层
- RuoYi 框架基础组件

## 修改文件清单

| 文件路径 | 修改类型 | 说明 |
|---------|---------|------|
| `ruoyi-modules/ruoyi-xkd/src/main/java/com/ruoyi/xkd/mapper/TTaskPlanMapper.java` | 修改 | 新增冲突查询方法 |
| `ruoyi-modules/ruoyi-xkd/src/main/resources/mapper/xkd/TTaskPlanMapper.xml` | 修改 | 新增冲突查询 SQL |
| `ruoyi-modules/ruoyi-xkd/src/main/java/com/ruoyi/xkd/service/TaskConflictService.java` | 新增 | 冲突检测服务类 |
| `ruoyi-modules/ruoyi-xkd/src/main/java/com/ruoyi/xkd/service/impl/TTaskPlanServiceImpl.java` | 修改 | 集成冲突检测 |
| `ruoyi-modules/ruoyi-xkd/src/main/java/com/ruoyi/xkd/controller/TTaskPlanController.java` | 修改 | 新增冲突检测接口 |

## 实现步骤

### 第一步：Mapper 层新增冲突查询方法

1. 在 `TTaskPlanMapper.java` 新增方法：
```java
List<TTaskPlan> selectConflictTaskList(TTaskPlan taskPlan);
```

2. 在 `TTaskPlanMapper.xml` 新增对应 SQL：
- 查询 WAITING 和 RUNNING 状态的任务
- 时间重叠判断：`start_time < #{endTime} AND end_time > #{startTime}`
- 站址或设备匹配
- 排除自身（更新时）

### 第二步：新增 TaskConflictService

1. 新建 `TaskConflictService.java`
2. 实现 `checkConflictOrThrow` 方法：
   - 校验时间不能为空
   - 校验结束时间晚于开始时间
   - 调用 Mapper 查询冲突
   - 有冲突则抛出异常

### 第三步：ServiceImpl 集成冲突检测

1. **新增任务时：
   - 调用冲突检测
   - 设置默认状态为 WAITING
   - 设置冲突状态为 0

2. **修改任务时：
   - 检查任务状态（不允许修改 RUNNING 和 FINISHED
   - 调用冲突检测

3. **删除任务时：
   - 检查任务状态（不允许删除 RUNNING 和 FINISHED

### 第四步：Controller 新增冲突检测接口

在 `TTaskPlanController.java` 新增：
```java
@PostMapping("/checkConflict")
public AjaxResult checkConflict(@RequestBody TTaskPlan tTaskPlan)
```

## 风险与注意事项

1. **时间比较处理：确保日期类型比较准确（Date 类型正确使用
2. **状态控制**：严格控制任务状态流转
3. **异常处理**：统一使用 RuoYi 的 ServiceException
4. **依赖注入**：正确使用 @Resource 或 @Autowired
5. **测试覆盖**：建议测试：
   - 同一站址时间冲突
   - 同一设备时间冲突
   - 更新任务时排除自身
   - 状态检查（执行中任务不允许操作

## 数据验证

实现完成后需要验证：

- ✅ 新增任务时自动检测冲突
- ✅ 修改任务时自动检测冲突
- ✅ 冲突检测接口正常工作
- ✅ 执行中/已完成任务不可修改/删除
- ✅ 无冲突任务可以正常操作
