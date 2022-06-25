<template>
    <div>
        <div style="background:#eee;padding: 20px">
            <Card :bordered="false">
                <p slot="title">用户详情<Button type="error" size="small" style="margin-left:20px">彻底删除账户</Button>
                </p>
                <div>
                    <table style="width:100%">
                    <tr>
                            <td class="leftTd">用户ID: </td>
                            <td>
                                <p class="detail"> {{ user.id }}</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="leftTd">注册邮箱: </td>
                            <td>
                                <p class="detail"> {{ user.email }}</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="leftTd">注册时间: </td>
                            <td>
                                <p class="detail"> {{ user.dateTime }}</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="leftTd">用户状态: </td>
                            <td>
                                <p class="detail"> 正常</p>
                            </td>
                        </tr>
                    </table>
                </div>
            </Card>
        </div>
        <Modal v-model="modal1" title="确认彻底删除账户？" @on-ok="ok" @on-cancel="cancel">
            <p>删除账户将删除所有文件!</p>
        </Modal>
    </div>
</template>

<script>
export default {
    name: 'AccountContent',
    data() {
        return {
            user: {
                id: 0,
                name: '张三',
                eMail: '2598925230@qq.com',
                dateTime: '2022-03-23, 20:10',
                activeTime: '2022-06-23, 20:10'
            },
            modal1: false
        }
    },
    methods: {
        ok() {
            this.$Message.info('点击了确定');
        },
        cancel() {
            this.$Message.info('点击了取消');
        },
    },
    mounted(){
        this.user = JSON.parse(sessionStorage.getItem('user'))
    }
}
</script>

<style scoped>
.leftTd {
    text-align: right;
    font-size: 40px;
    color: grey;
    width: 40%;
}

.detail {
    font-size: 40px;
}
</style>