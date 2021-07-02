<template>
  <div class="app-container">
    <div style="display: flex; justify-content: space-between">
      <el-form :inline="true" :model="searchOrder" class="demo-form-inline">
        <el-form-item label="订单编号">
          <el-input prefix-icon="el-icon-search" v-model="searchOrder.orderId" placeholder="请输入订单编号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div style="margin-bottom: 20px" v-for="order in this.orders">
      <div>订单编号：{{ order.orderId }}</div>
      <el-table
        ref="table"
        :data="order.orderItems"
        tooltip-effect="dark"
      >
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
          <template slot-scope="scope">
            ￥ {{ scope.row.productPrice }}
          </template>
        </el-table-column>
        <el-table-column
          prop="count"
          label="数量">
        </el-table-column>
        <el-table-column
          prop="totalPrice"
          label="小计">
          <template slot-scope="scope">
            ￥ {{ scope.row.totalPrice }}
          </template>
        </el-table-column>
      </el-table>

      <el-card class="box-card" shadow="never">
        实付款：<span>{{ getTotal(order.orderItems) }} 元</span>
        <el-button
          style="margin-left: 20px"
          size="mini"
          @click="handleDelete(order.orderId)"
          type="warning">删除订单
        </el-button>
      </el-card>
    </div>
  </div>
</template>

<script>

import {
  deleteOrder,
  orderDetail, searchOrder
} from "@/api/shop";

export default {
  name: "order",
  data() {
    return {
      orders: [],
      orderItems: [],
      totalPrice: 0,
      orderId: '',
      searchOrder: {
        orderId: ''
      }
    }
  },
  created() {
    this.loadOrder()
  },
  methods: {
    loadOrder() {
      orderDetail().then(res => {
        if (res.code === 20000) {
          console.log(res.data)
          const { orders } = res.data
          this.orders = orders
        }
      })
    },
    getTotal(items) {
      let total = 0
      items.forEach(item => {
        total += Number(item.totalPrice)
      })
      return total
    },
    handleDelete(orderId) {
      console.log(orderId)
      deleteOrder(orderId).then(res => {
        if (res.code === 20000) {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.loadOrder()
        }
      })
    },
    handleSearch() {
      searchOrder(this.searchOrder.orderId).then(res => {
        if (res.code === 20000) {
          const { orders } = res.data
          this.orders = orders
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
