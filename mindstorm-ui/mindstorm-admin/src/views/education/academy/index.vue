<template>
  <div class="app-container">
    <div v-hasPermission="['education:academy:query']">
      <el-form ref="academySearchFrom" :inline="true" :model="academySearchForm">
        <el-form-item label="院系名称">
          <el-input size="small" v-model="academySearchForm.name" placeholder="院系名称"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select size="small" v-model="academySearchForm.status" placeholder="请选择">
            <el-option
              v-for="item in AcademyStatus"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker
            v-model="academySearchForm.time"
            type="datetimerange"
            size="small"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right">
          </el-date-picker>
        </el-form-item>
        <el-form-item class="btn-item">
          <el-button icon="el-icon-search" size="small" type="primary" @click="handlerSearchAcademy">查询</el-button>
        </el-form-item>
        <el-form-item class="btn-item">
          <el-button icon="el-icon-refresh" size="small" type="default" @click="handlerReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { AcademyStatus } from "@/api/Academy";

export default {
  name: "Academy",
  data() {
    return {
      // 院系状态
      AcademyStatus,
      // 院系搜索表单信息
      academySearchForm: {
        name: '',
        status: undefined,
        time: []
      },
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      }
    }
  },
  methods: {
    // 搜索院系
    handlerSearchAcademy() {
      console.log(this.academySearchForm)
      const { $XModal } = this
      $XModal.message({
        content: '搜索！！！',
        status: 'success'
      })
    },
    // 重置搜索表单
    handlerReset() {
      this.academySearchForm = {
        name: '',
        status: undefined,
        time: []
      }
    }
  }
}
</script>

<style scoped>

</style>
