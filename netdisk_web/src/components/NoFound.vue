<template>
  <div id="main">

      <el-empty description="404, 您访问的页面不存在">
          <el-button type="primary" @click="back">回到首页</el-button>
      </el-empty>
  </div>
</template>

<script>
export default {
    name:'NoFound',
    methods:{
        back(){
            this.$router.push({
                name:'index',
            }).catch(err => {err})
        }
    }
}
</script>

<style scoped>
</style>