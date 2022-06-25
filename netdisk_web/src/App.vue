<template>
  <div id="app">
    <router-view v-wechat-title="$route.meta.title"></router-view>
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {
  },
  
}
</script>

<style>
#app{
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
  [v-cloak]{
    /* 元素隐藏    */
    display: none;
  }
  
  .ql-align-center{
	text-align: center;
	}
    .ql-align-right{
    text-align: right;
    }
    .ql-indent-1{
    padding-left: 16px;
    }
    .ql-indent-2{
    padding-left: 32px;
    }
    .ql-indent-3{
    padding-left: 48px;
    }
    .ql-indent-4{
    padding-left: 64px;
    }
    .ql-indent-5{
    padding-left: 80px;
    }
    .ql-indent-6{
    padding-left: 96px;
    }
    .ql-indent-7{
    padding-left: 112px;
    }
    .ql-indent-8{
    padding-left: 128px;
    }
</style>
