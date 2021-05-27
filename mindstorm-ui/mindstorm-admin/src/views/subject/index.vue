<template>
  <div class="app-container">
    <div v-hasPermission="['system:subject:query']">
      <el-form ref="roleForm" :inline="true" :model="searchSubjectVO">
        <el-form-item label="名称" prop="name">
          <el-input size="small" v-model="searchSubjectVO.name" placeholder="名称"></el-input>
        </el-form-item>
        <el-form-item class="btn-item">
          <el-button icon="el-icon-search" size="small" type="primary" @click="handleSubjectSearch">查询</el-button>
        </el-form-item>
        <el-form-item class="btn-item">
          <el-button icon="el-icon-refresh" size="small" type="default" @click="resetSearchSubjectForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "SubjectList"
}
</script>

<style scoped>

</style>
