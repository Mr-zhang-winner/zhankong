<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="告警类型：DEVICE/TASK/SYSTEM/NETWORK/ILLEGAL" prop="alarmType">
        <el-select v-model="queryParams.alarmType" placeholder="请选择告警类型" clearable>
          <el-option
            v-for="dict in dict.type.xkd_alarm_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="告警级别" prop="alarmLevel">
        <el-input
          v-model="queryParams.alarmLevel"
          placeholder="请输入告警级别"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="告警位置" prop="alarmLocation">
        <el-input
          v-model="queryParams.alarmLocation"
          placeholder="请输入告警位置"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关联ID：设备ID/任务ID等" prop="relatedId">
        <el-input
          v-model="queryParams.relatedId"
          placeholder="请输入关联ID：设备ID/任务ID等"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发生时间" prop="occurTime">
        <el-date-picker clearable
          v-model="queryParams.occurTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择发生时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="处理状态：0未处理 1已确认 2已处理 3已归档" prop="handleStatus">
        <el-select v-model="queryParams.handleStatus" placeholder="请选择处理状态：0未处理 1已确认 2已处理 3已归档" clearable>
          <el-option
            v-for="dict in dict.type.xkd_handle_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="处理人" prop="handler">
        <el-input
          v-model="queryParams.handler"
          placeholder="请输入处理人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处理时间" prop="handleTime">
        <el-date-picker clearable
          v-model="queryParams.handleTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择处理时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['xkd:alarm:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['xkd:alarm:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['xkd:alarm:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['xkd:alarm:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="alarmList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="告警ID" align="center" prop="alarmId" />
      <el-table-column label="告警类型" align="center" prop="alarmType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.xkd_alarm_type" :value="scope.row.alarmType"/>
        </template>
      </el-table-column>
      <el-table-column label="告警级别" align="center" prop="alarmLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.xkd_alarm_level" :value="scope.row.alarmLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="告警描述" align="center" prop="alarmDesc" />
      <el-table-column label="告警位置" align="center" prop="alarmLocation" />
      <el-table-column label="关联ID：设备ID/任务ID等" align="center" prop="relatedId" />
      <el-table-column label="发生时间" align="center" prop="occurTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.occurTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="处理状态：0未处理 1已确认 2已处理 3已归档" align="center" prop="handleStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.xkd_handle_status" :value="scope.row.handleStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="处理人" align="center" prop="handler" />
      <el-table-column label="处理时间" align="center" prop="handleTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.handleTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="处理结果" align="center" prop="handleResult" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['xkd:alarm:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['xkd:alarm:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改告警日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="告警类型" prop="alarmType">
              <el-select v-model="form.alarmType" placeholder="请选择告警类型">
                <el-option
                  v-for="dict in dict.type.xkd_alarm_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="告警级别：INFO/WARN/ERROR/CRITICAL" prop="alarmLevel">
              <el-input v-model="form.alarmLevel" placeholder="请输入告警级别：INFO/WARN/ERROR/CRITICAL" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="告警描述" prop="alarmDesc">
              <el-input v-model="form.alarmDesc" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="告警位置" prop="alarmLocation">
              <el-input v-model="form.alarmLocation" placeholder="请输入告警位置" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="关联ID：设备ID/任务ID等" prop="relatedId">
              <el-input v-model="form.relatedId" placeholder="请输入关联ID：设备ID/任务ID等" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="发生时间" prop="occurTime">
              <el-date-picker clearable
                v-model="form.occurTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择发生时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="处理状态：0未处理 1已确认 2已处理 3已归档" prop="handleStatus">
              <el-radio-group v-model="form.handleStatus">
                <el-radio
                  v-for="dict in dict.type.xkd_handle_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="处理人" prop="handler">
              <el-input v-model="form.handler" placeholder="请输入处理人" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="处理时间" prop="handleTime">
              <el-date-picker clearable
                v-model="form.handleTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择处理时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="处理结果" prop="handleResult">
              <el-input v-model="form.handleResult" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAlarm, getAlarm, delAlarm, addAlarm, updateAlarm } from "@/api/xkd/alarm"

export default {
  name: "Alarm",
  dicts: ['xkd_handle_status', 'xkd_alarm_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 告警日志表格数据
      alarmList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        alarmType: null,
        alarmLevel: null,
        alarmDesc: null,
        alarmLocation: null,
        relatedId: null,
        occurTime: null,
        handleStatus: null,
        handler: null,
        handleTime: null,
        handleResult: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        alarmType: [
          { required: true, message: "告警类型：DEVICE/TASK/SYSTEM/NETWORK/ILLEGAL不能为空", trigger: "change" }
        ],
        alarmLevel: [
          { required: true, message: "告警级别：INFO/WARN/ERROR/CRITICAL不能为空", trigger: "blur" }
        ],
        alarmDesc: [
          { required: true, message: "告警描述不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询告警日志列表 */
    getList() {
      this.loading = true
      listAlarm(this.queryParams).then(response => {
        this.alarmList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        alarmId: null,
        alarmType: null,
        alarmLevel: null,
        alarmDesc: null,
        alarmLocation: null,
        relatedId: null,
        occurTime: null,
        handleStatus: null,
        handler: null,
        handleTime: null,
        handleResult: null,
        createTime: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.alarmId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加告警日志"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const alarmId = row.alarmId || this.ids
      getAlarm(alarmId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改告警日志"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.alarmId != null) {
            updateAlarm(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addAlarm(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const alarmIds = row.alarmId || this.ids
      this.$modal.confirm('是否确认删除告警日志编号为"' + alarmIds + '"的数据项？').then(function() {
        return delAlarm(alarmIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('xkd/alarm/export', {
        ...this.queryParams
      }, `alarm_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
