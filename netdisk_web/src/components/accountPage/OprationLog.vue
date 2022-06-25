<template>
    <div>
        <Table highlight-row :columns="columns1" :data="data1" @on-current-change="changeRow" height="750"></Table>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    name: 'OprationLog',
    data() {
        return {
            columns1: [
                {
                    title: '时间',
                    key: 'dateTime'
                },
                {
                    title: '操作',
                    key: 'type',
                    render:(h, params)=>{
                        switch(params.row.type){
                        case 0: return h('p','登录')
                        case 1: return h('p','新建目录')
                        case 2: return h('p','新建文件')
                        case 3: return h('p','删除目录')
                        case 4: return h('p','删除文件')
                        default : return h('p','其他')
                        }
                    }
                },
                {
                    title: '详情',
                    key: 'content'
                }
            ],
            data1: [
                {
                },
            ]
        }
    },
    methods: {
        changeRow(currentRow) {
            currentRow
            //发送请求 currentRow.id, 后端判断文件夹就返回新表格，否则返回文件详情给出下载键
        },
    },
    mounted(){
        axios.get("/getLog").then(res=>{
            this.data1=res.data
        })
    }
}
</script>

<style>
</style>