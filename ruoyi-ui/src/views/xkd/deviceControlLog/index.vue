<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备ID" prop="deviceId">
        <el-input
          v-model="queryParams.deviceId"
          placeholder="请输入设备ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备编号" prop="deviceCode">
        <el-input
          v-model="queryParams.deviceCode"
          placeholder="请输入设备编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="协议KEY" prop="commandKey">
        <el-input
          v-model="queryParams.commandKey"
          placeholder="请输入协议KEY"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下发命令码" prop="cmdCode">
        <el-input
          v-model="queryParams.cmdCode"
          placeholder="请输入下发命令码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="应答命令码" prop="ackCmd">
        <el-input
          v-model="queryParams.ackCmd"
          placeholder="请输入应答命令码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发送时间" prop="sendTime">
        <el-date-picker clearable
          v-model="queryParams.sendTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择发送时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="响应时间" prop="ackTime">
        <el-date-picker clearable
          v-model="queryParams.ackTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择响应时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="操作人" prop="operator">
        <el-input
          v-model="queryParams.operator"
          placeholder="请输入操作人"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['xkd:deviceControlLog:add']"
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
          v-hasPermi="['xkd:deviceControlLog:edit']"
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
          v-hasPermi="['xkd:deviceControlLog:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['xkd:deviceControlLog:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="deviceControlLogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="控制ID" align="center" prop="controlId" />
      <el-table-column label="设备ID" align="center" prop="deviceId" />
      <el-table-column label="设备编号" align="center" prop="deviceCode" />
      <el-table-column label="命令类型" align="center" prop="commandType" />
      <el-table-column label="协议KEY" align="center" prop="commandKey" />
      <el-table-column label="命令内容JSON" align="center" prop="commandContent" />
      <el-table-column label="下发命令码" align="center" prop="cmdCode" />
      <el-table-column label="下发帧HEX" align="center" prop="frameHex" />
      <el-table-column label="应答命令码" align="center" prop="ackCmd" />
      <el-table-column label="ACK状态" align="center" prop="ackStatus" />
      <el-table-column label="ACK内容JSON" align="center" prop="ackContent" />
      <el-table-column label="发送状态：WAITING/SUCCESS/FAILED/TIMEOUT" align="center" prop="sendStatus" />
      <el-table-column label="发送时间" align="center" prop="sendTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sendTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="响应时间" align="center" prop="ackTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ackTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作人" align="center" prop="operator" />
      <el-table-column label="错误信息" align="center" prop="errorMsg" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['xkd:deviceControlLog:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['xkd:deviceControlLog:remove']"
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

    <!-- 添加或修改设备控制日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="设备ID" prop="deviceId">
              <el-input v-model="form.deviceId" placeholder="请输入设备ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="设备编号" prop="deviceCode">
              <el-input v-model="form.deviceCode" placeholder="请输入设备编号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="协议KEY" prop="commandKey">
              <el-input v-model="form.commandKey" placeholder="请输入协议KEY" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="命令内容JSON">
              <editor v-model="form.commandContent" :min-height="192"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="下发命令码" prop="cmdCode">
              <el-input v-model="form.cmdCode" placeholder="请输入下发命令码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="下发帧HEX" prop="frameHex">
              <el-input v-model="form.frameHex" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="应答命令码" prop="ackCmd">
              <el-input v-model="form.ackCmd" placeholder="请输入应答命令码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="ACK内容JSON">
              <editor v-model="form.ackContent" :min-height="192"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="发送时间" prop="sendTime">
              <el-date-picker clearable
                v-model="form.sendTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择发送时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="响应时间" prop="ackTime">
              <el-date-picker clearable
                v-model="form.ackTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择响应时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="操作人" prop="operator">
              <el-input v-model="form.operator" placeholder="请输入操作人" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="错误信息" prop="errorMsg">
              <el-input v-model="form.errorMsg" type="textarea" placeholder="请输入内容" />
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
import { listDeviceControlLog, getDeviceControlLog, delDeviceControlLog, addDeviceControlLog, updateDeviceControlLog } from "@/api/xkd/deviceControlLog"

export default {
  name: "DeviceControlLog",
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
      // 设备控制日志表格数据
      deviceControlLogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceId: null,
        deviceCode: null,
        commandType: null,
        commandKey: null,
        commandContent: null,
        cmdCode: null,
        frameHex: null,
        ackCmd: null,
        ackStatus: null,
        ackContent: null,
        sendStatus: null,
        sendTime: null,
        ackTime: null,
        operator: null,
        errorMsg: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        deviceCode: [
          { required: true, message: "设备编号不能为空", trigger: "blur" }
        ],
        commandType: [
          { required: true, message: "命令类型不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询设备控制日志列表 */
    getList() {
      this.loading = true
      listDeviceControlLog(this.queryParams).then(response => {
        this.deviceControlLogList = response.rows
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
        controlId: null,
        deviceId: null,
        deviceCode: null,
        commandType: null,
        commandKey: null,
        commandContent: null,
        cmdCode: null,
        frameHex: null,
        ackCmd: null,
        ackStatus: null,
        ackContent: null,
        sendStatus: null,
        sendTime: null,
        ackTime: null,
        operator: null,
        errorMsg: null,
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
      this.ids = selection.map(item => item.controlId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加设备控制日志"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const controlId = row.controlId || this.ids
      getDeviceControlLog(controlId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改设备控制日志"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.controlId != null) {
            updateDeviceControlLog(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addDeviceControlLog(this.form).then(response => {
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
      const controlIds = row.controlId || this.ids
      this.$modal.confirm('是否确认删除设备控制日志编号为"' + controlIds + '"的数据项？').then(function() {
        return delDeviceControlLog(controlIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('xkd/deviceControlLog/export', {
        ...this.queryParams
      }, `deviceControlLog_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
