<template>
    <div id="main">

        <div id="center-box">
            <Card class="box-card" style="height:80%">
                <p style="font-size:50px">Z家装</p>
                <table style="width:100%;margin-top:60px;margin-bottom:60px">
                    <tr>
                        <td style="width:20%">
                            邮箱:
                        </td>
                        <td style="width:80%">
                            <Input v-model="name" style="width:200px"></Input>
                        </td>
                    </tr>
                    <tr style="height:30px">
                        <td>
                        </td>
                        <td>

                        </td>
                    </tr>
                    <tr style="margin-top:20px">
                        <td style="width:20%">
                            密码:
                        </td>
                        <td style="width:80%">
                            <Input v-model="password" style="width:200px"></Input>
                        </td>
                    </tr>


                </table>
                <Button type="primary" @click="login" style="margin-right:50px">登录</Button>
                <Button type="error" @click="register">输入信息，立即注册</Button>
            </Card>
            
        </div>

    </div>
</template>

<script>
import axios from 'axios'
export default {
    name: 'LoginPage',
    created() {
        if (sessionStorage.getItem("isLogin"))
            this.$router.push({
                path: '/admin',
            }).catch(err => { err })
    },
    data() {
        return {
            name: '',
            password: ''
        }
    },
    methods: {
        register() {
            axios.post("/newUser",{eMail: this.name,password: this.password}).then(res => {
                if (res.data) {
                    sessionStorage.setItem("isLogin", true)
                    this.$cookies.set("userId", res.data.id)
                    sessionStorage.setItem("token", res.data.password)
                    //axios.defaults.headers.common['token'] = res.data
                    this.$router.push({
                        path: '/',
                    }).catch(err => { err })
                }
                else
                    this.$Notice.open({
                        title: '登录失败',
                        desc: '账号或密码错误'
                    });
            }).catch(() => {
                this.$Notice.open({
                    title: '登录失败',
                    desc: '网络异常'
                });
            })
        },
        login(){
            axios.post("/login",{email: this.name,password: this.password}).then(res => {
                if (res.data) {
                    sessionStorage.setItem("isLogin", true)
                    this.$cookies.set("userId", res.data.id)
                    sessionStorage.setItem("token", res.data.password)
                    //axios.defaults.headers.common['token'] = res.data
                    this.$router.push({
                        path: '/',
                    }).catch(err => { err })
                }
                else
                    this.$Notice.open({
                        title: '登录失败',
                        desc: '账号或密码错误'
                    });
            }).catch(() => {
                this.$Notice.open({
                    title: '登录失败',
                    desc: '网络异常'
                });
            })
        }
    }
}
</script>

<style scoped>
#main {
    height: 100vh;
    width: 100%;
    background: url('~@/assets/login.jpg');
}

#center-box {
    width: 400px;
    height: 500px;
    position: absolute;
    top: 50%;
    left: 50%;
    margin: -250px 0 0 -200px;
    text-align: center;
}
</style>