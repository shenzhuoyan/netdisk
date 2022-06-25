<template>
    <div>
        <Table highlight-row :columns="columns1" :data="data1" @on-current-change="changeRow"
            height="750"></Table>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    name: 'FileList',
    props:['changeDir','handleSelect'],
    data() {
        return {
            columns1: [
                {
                    title: '目录/文件',
                    key: 'type',
                    render: (h, params) => {
                        //return h('div',[h('Icon',{props:{type: 'ios-search',size:'18'}}),params.row.name]);
                        if (params.row.type == 0)
                            //0文件夹，1文件
                            return h('Icon', { props: { type: 'ios-folder', size: '20' } })
                        return h('Icon', { props: { type: 'ios-copy', size: '20' } })
                    }
                },

                {
                    title: '名称',
                    key: 'name',
                },
                {
                    title: '大小',
                    key: 'size'
                },
                {
                    title: '创建时间',
                    key: 'createTime',
                    sortable: true
                },
            ],
            data1: [{}],
            modal1: false,
        }
    },
    methods: {
        changeRow(currentRow) {
            //发送请求 currentRow.id, 后端判断文件夹就返回新表格，否则返回文件详情给出下载键
            if (currentRow.type == 0) {
                sessionStorage.setItem('currentDir', JSON.stringify(currentRow))
                axios.get('/getDir?dirId=' + JSON.parse(sessionStorage.getItem("currentDir")).id).then(res => {
                    this.data1 = res.data
                    this.changeDir(currentRow)
                })
            }
            else {
                axios.get('/getFile?fileId='+currentRow.id).then(res=>{
                    sessionStorage.setItem('currentFile', JSON.stringify(res.data))
                    this.handleSelect('fileContent')
                })
            }
        },
        changePage() {
            axios.get('/getDir?dirId=' + JSON.parse(sessionStorage.getItem("currentDir")).id).then(res => {
                this.data1 = res.data
            })
        },

    },
    mounted() {

        setTimeout(() => {
            axios.get('/getDir?dirId=' + JSON.parse(sessionStorage.getItem("currentDir")).id).then(res => {
                this.data1 = res.data
            })
        }, 300)
    }
}
</script>

<style>
.demo-spin-icon-load {
    animation: ani-demo-spin 1s linear infinite;
}

@keyframes ani-demo-spin {
    from {
        transform: rotate(0deg);
    }

    50% {
        transform: rotate(180deg);
    }

    to {
        transform: rotate(360deg);
    }
}

.demo-spin-col {
    height: 100px;
    position: relative;
    border: 1px solid #eee;
}
</style>