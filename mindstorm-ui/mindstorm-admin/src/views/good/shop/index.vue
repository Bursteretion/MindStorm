<template>
  <div class="app-container">
    <div>
      <el-table
        ref="table"
        :data="carList"
        tooltip-effect="dark"
        @selection-change="handleSelectionChange"
      >
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          label="商品名称"
          prop="productName"
          width="250">
        </el-table-column>
        <el-table-column
          label="图片"
          prop="productImage">
          <template slot-scope="scope">
            <el-avatar shape="square" :size="100" :src="scope.row.productImage"></el-avatar>
          </template>
        </el-table-column>
        <el-table-column
          prop="productPrice"
          label="单价"
          width="100">
        </el-table-column>
        <el-table-column
          prop="productStock"
          width="100"
          label="库存">
        </el-table-column>
        <el-table-column
          prop="productCount"
          label="数量">
          <template slot-scope="scope">
            <el-input-number
              size="mini"
              v-model="scope.row.productCount"
              @change="handleChange(scope.row)"
              :min="1"
              :max="scope.row.productStock">
            </el-input-number>
          </template>
        </el-table-column>
        <el-table-column
          prop="productTotal"
          label="金额">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-card style="margin-top: 10px" class="box-card">
        已选商品：<span>{{ selectedCount }} 件</span>, 合计：￥<span>{{ totalPrice }} 元</span>
        <el-button
          @click="handleBatchDelete"
          v-if="this.multipleSelection.length > 0"
          style="margin-left: 20px"
          size="mini"
          type="warning">删除
        </el-button>
        <el-button size="mini" type="danger" style="margin-left: 20px">结算</el-button>
      </el-card>
    </div>
  </div>
</template>

<script>

import {
  listCar,
  deleteCar,
  batchDeleteCar,
  updateCount
} from "@/api/shop";

export default {
  name: "shop",
  data() {
    return {
      carList: [],
      multipleSelection: [],
      selectedCount: 0,
      totalPrice: 0
    }
  },
  created() {
    this.loadShopCars()
  },
  methods: {
    loadShopCars() {
      listCar().then(res => {
        const { carList } = res.data
        console.log(carList)
        this.carList = carList
      })
    },
    handleChange(row) {
      let can = this.multipleSelection.filter(item => item.shopCarId === row.shopCarId).length > 0
      this.carList.forEach(item => {
        if (item.shopCarId === row.shopCarId) {
          if (can) this.totalPrice -= item.productTotal
          item.productTotal = item.productCount * item.productPrice
          if (can) this.totalPrice += item.productTotal
        }
      })
      if (this.multipleSelection.length <= 0) {
        this.totalPrice = 0
      }

      updateCount(row.shopCarId, row.count).then(res => {
        console.log(res)
      })
    },
    handleDelete(row) {
      this.$confirm('你确定要删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteCar(row.userShopCarId).then(res => {
          if (res.code === 20000) {
            this.loadShopCars()
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
          }
        })
      })
    },
    handleSelectionChange(val = []) {
      this.selectedCount = val.length
      this.totalPrice = 0
      val.forEach(item => {
        this.totalPrice += Number(item.productTotal)
      })
      this.multipleSelection = val
    },
    handleBatchDelete() {
      if (this.multipleSelection.length <= 0) return
      let ids = []
      this.multipleSelection.forEach(item => {
        ids.push(item.userShopCarId)
      })
      batchDeleteCar(ids).then(res => {
        if (res.code === 20000) {
          this.loadShopCars()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
