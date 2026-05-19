<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备编号" prop="deviceCode">
        <el-input
          v-model="queryParams.deviceCode"
          placeholder="请输入设备编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="方向：SEND/RECEIVE" prop="direction">
        <el-input
          v-model="queryParams.direction"
          placeholder="请输入方向：SEND/RECEIVE"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="远端IP" prop="remoteIp">
        <el-input
          v-model="queryParams.remoteIp"
          placeholder="请输入远端IP"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="远端端口" prop="remotePort">
        <el-input
          v-model="queryParams.remotePort"
          placeholder="请输入远端端口"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="命令码" prop="cmdCode">
        <el-input
          v-model="queryParams.cmdCode"
          placeholder="请输入命令码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="命令名称" prop="cmdName">
        <el-input
          v-model="queryParams.cmdName"
          placeholder="请输入命令名称"
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
          v-hasPermi="['xkd:TAntennaProtocolLog:add']"
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
          v-hasPermi="['xkd:TAntennaProtocolLog:edit']"
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
          v-hasPermi="['xkd:TAntennaProtocolLog:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['xkd:TAntennaProtocolLog:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="TAntennaProtocolLogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="日志ID" align="center" prop="logId" />
      <el-table-column label="设备编号" align="center" prop="deviceCode" />
      <el-table-column label="方向：SEND/RECEIVE" align="center" prop="direction" />
      <el-table-column label="传输方式：UDP/RS422" align="center" prop="transportType" />
      <el-table-column label="远端IP" align="center" prop="remoteIp" />
      <el-table-column label="远端端口" align="center" prop="remotePort" />
      <el-table-column label="命令码" align="center" prop="cmdCode" />
      <el-table-column label="命令名称" align="center" prop="cmdName" />
      <el-table-column label="完整原始帧HEX" align="center" prop="frameHex" />
      <el-table-column label="参数体HEX" align="center" prop="payloadHex" />
      <el-table-column label="解析后的内容JSON" align="center" prop="payloadJson" />
      <el-table-column label="校验状态：OK/FAILED" align="center" prop="checkStatus" />
      <el-table-column label="结果状态" align="center" prop="resultStatus" />
      <el-table-column label="错误信息" align="center" prop="errorMsg" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['xkd:TAntennaProtocolLog:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['xkd:TAntennaProtocolLog:remove']"
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

    <!-- 添加或修改天线协议收发日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="设备编号" prop="deviceCode">
              <el-input v-model="form.deviceCode" placeholder="请输入设备编号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="方向：SEND/RECEIVE" prop="direction">
              <el-input v-model="form.direction" placeholder="请输入方向：SEND/RECEIVE" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="远端IP" prop="remoteIp">
              <el-input v-model="form.remoteIp" placeholder="请输入远端IP" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="远端端口" prop="remotePort">
              <el-input v-model="form.remotePort" placeholder="请输入远端端口" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="命令码" prop="cmdCode">
              <el-input v-model="form.cmdCode" placeholder="请输入命令码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="命令名称" prop="cmdName">
              <el-input v-model="form.cmdName" placeholder="请输入命令名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="完整原始帧HEX" prop="frameHex">
              <el-input v-model="form.frameHex" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="参数体HEX" prop="payloadHex">
              <el-input v-model="form.payloadHex" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="解析后的内容JSON" prop="payloadJson">
              <el-input v-model="form.payloadJson" type="textarea" placeholder="请输入内容" />
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
import { listTAntennaProtocolLog, getTAntennaProtocolLog, delTAntennaProtocolLog, addTAntennaProtocolLog, updateTAntennaProtocolLog } from "@/api/xkd/TAntennaProtocolLog"

export default {
  name: "TAntennaProtocolLog",
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
      // 天线协议收发日志表格数据
      TAntennaProtocolLogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceCode: null,
        direction: null,
        transportType: null,
        remoteIp: null,
        remotePort: null,
        cmdCode: null,
        cmdName: null,
        frameHex: null,
        payloadHex: null,
        payloadJson: null,
        checkStatus: null,
        resultStatus: null,
        errorMsg: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        direction: [
          { required: true, message: "方向：SEND/RECEIVE不能为空", trigger: "blur" }
        ],
        cmdCode: [
          { required: true, message: "命令码不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询天线协议收发日志列表 */
    getList() {
      this.loading = true
      listTAntennaProtocolLog(this.queryParams).then(response => {
        this.TAntennaProtocolLogList = response.rows
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
        logId: null,
        deviceCode: null,
        direction: null,
        transportType: null,
        remoteIp: null,
        remotePort: null,
        cmdCode: null,
        cmdName: null,
        frameHex: null,
        payloadHex: null,
        payloadJson: null,
        checkStatus: null,
        resultStatus: null,
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
      this.ids = selection.map(item => item.logId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加天线协议收发日志"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const logId = row.logId || this.ids
      getTAntennaProtocolLog(logId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改天线协议收发日志"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.logId != null) {
            updateTAntennaProtocolLog(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addTAntennaProtocolLog(this.form).then(response => {
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
      const logIds = row.logId || this.ids
      this.$modal.confirm('是否确认删除天线协议收发日志编号为"' + logIds + '"的数据项？').then(function() {
        return delTAntennaProtocolLog(logIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('xkd/TAntennaProtocolLog/export', {
        ...this.queryParams
      }, `TAntennaProtocolLog_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
