import VueRouter from 'vue-router'

import IndexPage from '../components/IndexPage'
import LoginPage from '../components/LoginPage'


const router = new VueRouter({
    mode: "history",
    routes: [{
            path: '/',
            component: IndexPage,
            meta: {
                title: '飞熊网盘',
                requireAuth: true
            },
        },
        {
            path: '/login',
            component: LoginPage,
            meta: {
                title: '登录注册',
                requireAuth: false
            }
        }
    ]
})

//登录拦截
router.beforeEach((to, from, next) => {
    if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
        //如果需要就执行下面的代码

        var isLogin = sessionStorage.getItem("isLogin")
        console.log(isLogin)
        // 通过sessionStorage 获取当前的isLogin的值 将我们保存的isLogin的值赋给num,num是顺便取的名称，可以换
        //我们在登录界面，我们使用请求，请求成功后，我们就使用sessionStorage为‘isLogin’保存一个值  1，如果请求失败，就不保存‘isLogin’的值
        //如果请求成功，num的值就是1，请求失败就是null，所以下面进行判断
        if (isLogin) {
            //如果登录了，就跳转到'/index'路径
            //next({path:'/admin'});
            next();
        } else {
            next({
                path: '/login', //返回登录界面
                // query: {redirect: to.fullPath}  
            })
        }
    } else {
        //如果不需要登录权限就直接跳转到该路由
        next();
    }
})
export default router