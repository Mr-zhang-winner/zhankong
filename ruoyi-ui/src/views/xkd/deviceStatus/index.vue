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
      <el-form-item label="设备名称" prop="deviceName">
        <el-input
          v-model="queryParams.deviceName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="方位角" prop="azimuth">
        <el-input
          v-model="queryParams.azimuth"
          placeholder="请输入方位角"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="俯仰角" prop="elevation">
        <el-input
          v-model="queryParams.elevation"
          placeholder="请输入俯仰角"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工作电压" prop="voltage">
        <el-input
          v-model="queryParams.voltage"
          placeholder="请输入工作电压"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="运行状态：NORMAL/ABNORMAL/OFFLINE" prop="runStatus">
        <el-select v-model="queryParams.runStatus" placeholder="请选择运行状态" clearable>
          <el-option
            v-for="dict in dict.type.xkd_run_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="采集时间" prop="collectTime">
        <el-date-picker clearable
          v-model="queryParams.collectTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择采集时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="上报时间" prop="reportTime">
        <el-date-picker clearable
          v-model="queryParams.reportTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择上报时间">
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
          v-hasPermi="['xkd:deviceStatus:add']"
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
          v-hasPermi="['xkd:deviceStatus:edit']"
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
          v-hasPermi="['xkd:deviceStatus:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['xkd:deviceStatus:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="deviceStatusList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="状态ID" align="center" prop="statusId" />
      <el-table-column label="设备ID" align="center" prop="deviceId" />
      <el-table-column label="设备编号" align="center" prop="deviceCode" />
      <el-table-column label="设备名称" align="center" prop="deviceName" />
      <el-table-column label="方位角" align="center" prop="azimuth" />
      <el-table-column label="俯仰角" align="center" prop="elevation" />
      <el-table-column label="工作电压" align="center" prop="voltage" />
      <el-table-column label="工作参数JSON" align="center" prop="workParams" />
      <el-table-column label="运行状态：NORMAL/ABNORMAL/OFFLINE" align="center" prop="runStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.xkd_run_status" :value="scope.row.runStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="采集时间" align="center" prop="collectTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.collectTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上报时间" align="center" prop="reportTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.reportTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['xkd:deviceStatus:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['xkd:deviceStatus:remove']"
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

    <!-- 添加或修改设备状态对话框 -->
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
            <el-form-item label="设备名称" prop="deviceName">
              <el-input v-model="form.deviceName" placeholder="请输入设备名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="方位角" prop="azimuth">
              <el-input v-model="form.azimuth" placeholder="请输入方位角" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="俯仰角" prop="elevation">
              <el-input v-model="form.elevation" placeholder="请输入俯仰角" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="工作电压" prop="voltage">
              <el-input v-model="form.voltage" placeholder="请输入工作电压" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="工作参数JSON" prop="workParams">
              <el-input v-model="form.workParams" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="运行状态：NORMAL/ABNORMAL/OFFLINE" prop="runStatus">
              <el-radio-group v-model="form.runStatus">
                <el-radio
                  v-for="dict in dict.type.xkd_run_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="采集时间" prop="collectTime">
              <el-date-picker clearable
                v-model="form.collectTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择采集时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="上报时间" prop="reportTime">
              <el-date-picker clearable
                v-model="form.reportTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择上报时间">
              </el-date-picker>
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
import { listDeviceStatus, getDeviceStatus, delDeviceStatus, addDeviceStatus, updateDeviceStatus } from "@/api/xkd/deviceStatus"
import { connectWebSocket, disconnectWebSocket } from "@/api/websocket"

export default {
  name: "DeviceStatus",
  dicts: ['xkd_run_status'],
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
      // 设备状态表格数据
      deviceStatusList: [],
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
        deviceName: null,
        azimuth: null,
        elevation: null,
        voltage: null,
        workParams: null,
        runStatus: null,
        collectTime: null,
        reportTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        deviceId: [
          { required: true, message: "设备ID不能为空", trigger: "blur" }
        ],
        deviceCode: [
          { required: true, message: "设备编号不能为空", trigger: "blur" }
        ],
        runStatus: [
          { required: true, message: "运行状态：NORMAL/ABNORMAL/OFFLINE不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
    this.initWebSocket()
  },
  beforeDestroy() {
    disconnectWebSocket()
  },
  methods: {
    initWebSocket() {
      connectWebSocket(this.handleWebSocketMessage)
    },
    handleWebSocketMessage(message) {
      if (message.type === 'DEVICE_STATUS_UPDATE' && message.data) {
        const newStatus = message.data
        const index = this.deviceStatusList.findIndex(item => item.deviceId === newStatus.deviceId)
        if (index !== -1) {
          this.$set(this.deviceStatusList, index, { ...this.deviceStatusList[index], ...newStatus })
        } else {
          this.deviceStatusList.unshift(newStatus)
          this.total++
        }
      }
    },
    /** 查询设备状态列表 */
    getList() {
      this.loading = true
      listDeviceStatus(this.queryParams).then(response => {
        this.deviceStatusList = response.rows
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
        statusId: null,
        deviceId: null,
        deviceCode: null,
        deviceName: null,
        azimuth: null,
        elevation: null,
        voltage: null,
        workParams: null,
        runStatus: null,
        collectTime: null,
        reportTime: null,
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
      this.ids = selection.map(item => item.statusId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加设备状态"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const statusId = row.statusId || this.ids
      getDeviceStatus(statusId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改设备状态"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.statusId != null) {
            updateDeviceStatus(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addDeviceStatus(this.form).then(response => {
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
      const statusIds = row.statusId || this.ids
      this.$modal.confirm('是否确认删除设备状态编号为"' + statusIds + '"的数据项？').then(function() {
        return delDeviceStatus(statusIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('xkd/deviceStatus/export', {
        ...this.queryParams
      }, `deviceStatus_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
