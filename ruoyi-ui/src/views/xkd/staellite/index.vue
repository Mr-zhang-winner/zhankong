<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="卫星编号" prop="satelliteCode">
        <el-input
          v-model="queryParams.satelliteCode"
          placeholder="请输入卫星编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="卫星名称" prop="satelliteName">
        <el-input
          v-model="queryParams.satelliteName"
          placeholder="请输入卫星名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="星历类型" prop="ephemerisType">
        <el-select v-model="queryParams.ephemerisType" placeholder="请选择星历类型" clearable>
          <el-option
            v-for="dict in dict.type.xkd_ephemeris_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['xkd:staellite:add']"
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
          v-hasPermi="['xkd:staellite:edit']"
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
          v-hasPermi="['xkd:staellite:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['xkd:staellite:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="staelliteList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="卫星ID" align="center" prop="satelliteId" />
      <el-table-column label="卫星编号" align="center" prop="satelliteCode" />
      <el-table-column label="卫星名称" align="center" prop="satelliteName" />
      <el-table-column label="星历类型" align="center" prop="ephemerisType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.xkd_ephemeris_type" :value="scope.row.ephemerisType"/>
        </template>
      </el-table-column>
      <el-table-column label="轨道参数JSON" align="center" prop="orbitParams" />
      <el-table-column label="接收参数JSON" align="center" prop="receiveParams" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['xkd:staellite:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['xkd:staellite:remove']"
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

    <!-- 添加或修改卫星配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="卫星编号" prop="satelliteCode">
              <el-input v-model="form.satelliteCode" placeholder="请输入卫星编号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="卫星名称" prop="satelliteName">
              <el-input v-model="form.satelliteName" placeholder="请输入卫星名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="星历类型" prop="ephemerisType">
              <el-select v-model="form.ephemerisType" placeholder="请选择星历类型">
                <el-option
                  v-for="dict in dict.type.xkd_ephemeris_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="轨道参数JSON" prop="orbitParams">
              <el-input v-model="form.orbitParams" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="接收参数JSON" prop="receiveParams">
              <el-input v-model="form.receiveParams" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listStaellite, getStaellite, delStaellite, addStaellite, updateStaellite } from "@/api/xkd/staellite"

export default {
  name: "Staellite",
  dicts: ['xkd_ephemeris_type', 'sys_normal_disable'],
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
      // 卫星配置表格数据
      staelliteList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        satelliteCode: null,
        satelliteName: null,
        ephemerisType: null,
        orbitParams: null,
        receiveParams: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        satelliteCode: [
          { required: true, message: "卫星编号不能为空", trigger: "blur" }
        ],
        satelliteName: [
          { required: true, message: "卫星名称不能为空", trigger: "blur" }
        ],
        ephemerisType: [
          { required: true, message: "星历类型不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询卫星配置列表 */
    getList() {
      this.loading = true
      listStaellite(this.queryParams).then(response => {
        this.staelliteList = response.rows
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
        satelliteId: null,
        satelliteCode: null,
        satelliteName: null,
        ephemerisType: null,
        orbitParams: null,
        receiveParams: null,
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      this.ids = selection.map(item => item.satelliteId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加卫星配置"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const satelliteId = row.satelliteId || this.ids
      getStaellite(satelliteId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改卫星配置"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.satelliteId != null) {
            updateStaellite(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addStaellite(this.form).then(response => {
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
      const satelliteIds = row.satelliteId || this.ids
      this.$modal.confirm('是否确认删除卫星配置编号为"' + satelliteIds + '"的数据项？').then(function() {
        return delStaellite(satelliteIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('xkd/staellite/export', {
        ...this.queryParams
      }, `staellite_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
