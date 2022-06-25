<template>
    <div>
        <Table highlight-row :columns="columns1" :data="data1" @on-current-change="changeRow"></Table>
        <Modal v-model="modal1" title="确认彻底删除文件？" @on-ok="ok" @on-cancel="cancel">
            <p>此操作将彻底删除文件!</p>
        </Modal>
    </div>
</template>

<script>
export default {
    name: 'TrashList',
    data() {
        return {
            modal1:false,
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
                    title: '更改时间',
                    key: 'dateTime',
                    sortable: true
                },
                {
                    title: '操作',
                    render: (h, params) => {
                        return h('div', [h('Button', {
                            props: { type: 'primary', size: 'small' }, style: { marginRight: '10px' }, on: {
                                click() {
                                    console.log(params.row.id)
                                }
                            }
                        }, '恢复'), h('Button', {
                            props: { type: 'error', size: 'small' }, on: {
                                click() {
                                    console.log(params.row.id)
                                }
                            }
                        }, '彻底删除')])
                    }
                }
            ],
            data1: [
                {
                    id: 0,
                    name: '考研',
                    dateTime: '22-01-23,10:20',
                    size: '100MB',
                    type: 0
                },
                {
                    id: 1,
                    name: '开发',
                    dateTime: '22-02-13,10:20',
                    size: '99.8MB',
                    type: 1
                },
                {
                    id: 2,
                    name: '李小红',
                    dateTime: '22-06-21,10:20',
                    size: '100G',
                    type: 1
                },
                {
                    id: 3,
                    name: '周小伟',
                    dateTime: '22-04-22,10:20',
                    size: '23KB',
                    type: 1
                }
            ]
        }
    },
    methods: {
        changeRow(currentRow) {
            currentRow
            //发送请求 currentRow.id, 后端判断文件夹就返回新表格，否则返回文件详情给出下载键
        },
        ok() {
            this.$Message.info('点击了确定');
        },
        cancel() {
            this.$Message.info('点击了取消');
        },
    }
}
</script>

<style>
</style>