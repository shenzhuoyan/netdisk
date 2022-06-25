<style scoped>
.layout {
  border: 1px solid #d7dde4;
  background: #f5f7f9;
  height: 100vh;
}

.layout-logo {
  width: 200px;
  height: 40px;
  /*background: #5b6270;*/
  border-radius: 3px;
  float: left;
  position: relative;
  /*top: 15px;*/
  left: 20px;
}

.layout-nav {
  width: 420px;
  margin: 0 auto;
}


.layout-breadcrumb {
  padding: 10px 15px 0;
}

.layout-content {
  min-height: 200px;
  margin: 15px;
  overflow: hidden;
  background: #fff;
  border-radius: 4px;
}

.layout-content-main {
  padding: 10px;
}

.layout-hide-text .layout-text {
  display: none;
}

.layout-copy {
  text-align: center;
  padding: 10px 0 20px;
  color: #9ea7b4;
}

.layout-header {
  height: 60px;
  background: #fff;
  box-shadow: 0 1px 1px rgba(0, 0, 0, .1);
}

.layout-ceiling {
  /*background: #464c5b;*/
  /*padding: 10px 0;*/
  overflow: hidden;
}

.layout-ceiling-main {
  float: right;
  margin-right: 15px;
}

.layout-ceiling-main a {
  color: #9ba7b5;
}
</style>
<template>
  <div>
    <div class="layout" :class="{ 'layout-hide-text': spanLeft < 2 }">
      <Menu mode="horizontal" theme="dark" active-name="1">
        <div class="layout-logo">
          <img src="/img/logo_white.png" width="100%" />
        </div>
        <div class="layout-nav">

        </div>
        <div class="layout-ceiling">
          <div class="layout-ceiling-main">
            <a >用户 <span style="color:white">{{ user.email }}</span></a> |
            <a @click="exit">退出账户</a> |
            <a @click="modal2 = true">帮助中心</a> |
            <!--<a href="#">服务大厅</a>-->
          </div>
        </div>
      </Menu>
      <div class="layout-content" style="height:calc(100% - 140px)">
        <Row style="height:100%;">
          <i-col :span="spanLeft" style="height:100%">
            <Menu active-name="fileList" width="auto" theme="dark" style="height:100%" @on-select="handleSelect">
              <Menu-item name="fileList">
                <Icon type="ios-navigate" :size="iconSize"></Icon>
                <span class="layout-text">文件</span>
              </Menu-item>
              <Menu-item name="accountContent">
                <Icon type="ios-keypad" :size="iconSize"></Icon>
                <span class="layout-text">账户</span>
              </Menu-item>
              <Menu-item name="trashList">
                <Icon type="ios-trash" :size="iconSize"></Icon>
                <span class="layout-text">(不做了)</span>
              </Menu-item>
              <Menu-item name="oprationLog">
                <Icon type="ios-analytics" :size="iconSize"></Icon>
                <span class="layout-text">日志</span>
              </Menu-item>
            </Menu>
          </i-col>

          <i-col :span="24 - spanLeft">
            <div class="layout-header">
              <i-button type="text" @click="toggleClick" style="width:50px;float: left;padding-top:15px">
                <Icon type="md-menu" size="32"></Icon>

              </i-button>


              <Breadcrumb style="padding-top:20px;padding-left:40px;float:left;" v-show="pageStatus == 'fileList'">


                <Breadcrumb-item>
                  <i-button type="default" v-show="currentDir.level != 1" @click="parentDir">上一层</i-button>
                </Breadcrumb-item>
              </Breadcrumb>
              <div style="float:right;padding-top:20px;padding-left:20px" v-show="pageStatus == 'fileList'">
                <Button type="error" size="small" style="margin-right:20px" @click="modal1 = true"
                  v-show="currentDir.level != 1">删除本目录</Button>
                <Button type="success" size="small" style="margin-right:20px"
                  @click="handleSelect('uploadPage')">上传文件</Button>
                <Input v-model="dirName" placeholder="请输入目录名称" style="width: 100px"></Input>
                <Button type="primary" size="small" style="margin-right:20px" @click="addDir">创建目录</Button>

              </div>
            </div>

            <div class="layout-content-main" style="height:100%;">
              <!--<router-view></router-view>-->
              <FileList v-if="pageStatus == 'fileList'" :changeDir="setCurrentDir" :handleSelect="handleSelect">
              </FileList>
              <FileContent v-if="pageStatus == 'fileContent'" :back="handleSelect"></FileContent>
              <AccountContent v-if="pageStatus == 'accountContent'"></AccountContent>
              <TrashList v-if="pageStatus == 'trashList'"></TrashList>
              <OprationLog v-if="pageStatus == 'oprationLog'"></OprationLog>
              <UploadPage v-if="pageStatus == 'uploadPage'" :back="handleSelect"></UploadPage>
            </div>
          </i-col>
        </Row>
        <Modal v-model="modal1" title="确认删除？" @on-ok="ok" @on-cancel="cancel">
          <p>文件将在回收站保存10天</p>
        </Modal>
        <Modal v-model="modal2">
        <p style="font-size:50px">联系作者QQ: 2598925230</p>
        </Modal>
      </div>
      <div class="layout-copy">
        2022 &copy; 沈拙言
      </div>
    </div>
  </div>
</template>
<script>
import FileList from './contentPage/FileList';
import FileContent from './contentPage/FileContent';
import AccountContent from './accountPage/AccountContent';
import TrashList from './trashPage/TrashList';
import OprationLog from './accountPage/OprationLog';
import UploadPage from './contentPage/UploadPage.vue';
import axios from "axios"
export default {
  name: "IndexPage",
  data() {
    return {
      spanLeft: 2,
      spanRight: 22,
      modal1: false,
      modal2: false,
      pageStatus: 'fileList',
      //后端信息
      user: {
        name: "张三",
        rootDirId: 0,
        eMail: '234'
      },
      currentDir: { level: 0 },
      dirName: ''
    }
  },
  methods: {
    toggleClick() {
      if (this.spanLeft === 2) {
        this.spanLeft = 1;
        this.spanRight = 23;
      }
      else {
        this.spanLeft = 2;
        this.spanRight = 22;
      }
    },

    ok() {
      axios.get('/delDir?dirId=' + JSON.parse(sessionStorage.getItem("currentDir")).id).then(() => {
        this.$Message.info("已删除");
        this.parentDir()
      }).catch(() => {
        this.$Message.info("删除失败");
      })
    },
    cancel() {
      this.$Message.info("取消删除");
    },
    handleSelect(key) {
      this.pageStatus = key
    },
    parentDir() {
      axios.get('/getDirSelf?dirId=' + JSON.parse(sessionStorage.getItem("currentDir")).parentId).then(res => {
        this.currentDir = res.data
        sessionStorage.setItem('currentDir', JSON.stringify(res.data))
        this.handleSelect('temp')
        setTimeout(() => {
          this.handleSelect('fileList')
        })
      })
    },
    addDir() {

      if (this.dirName != '') {
        axios.post('/addDir', {
          name: this.dirName,
          parentId: this.currentDir.id
        }).then(() => {
          this.handleSelect('temp')
          this.$Message.info("创建成功");
          setTimeout(() => {
            this.handleSelect('fileList')
          })
        })

      }
    },
    setCurrentDir(dir) {
      this.currentDir = dir
    },
    exit() {
      sessionStorage.clear()
      this.$cookies.remove('userId')
      this.$router.push({
        path: '/login',
      }).catch(err => { err })
    }
  },
  computed: {
    iconSize() {
      return this.spanLeft === 2 ? 14 : 24;
    }
  },
  components: { FileList, FileContent, AccountContent, TrashList, OprationLog, UploadPage },
  mounted() {
    //this.$cookies.set('userId', 9)
    axios.get("/getUser").then(res => {
      sessionStorage.setItem('user', JSON.stringify(res.data))
      this.user = res.data
      axios.get('/getRoot').then(res => {
        this.currentDir = res.data
        sessionStorage.setItem('currentDir', JSON.stringify(res.data))
      })
    })
  }

}
</script>
