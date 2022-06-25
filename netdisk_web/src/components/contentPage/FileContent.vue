<template>
    <div>
        <div style="background:#eee;padding: 20px">
            <Card :bordered="false">
                <p slot="title">
                    <Icon type="ios-arrow-back" size="25" @click="back('fileList')"></Icon>{{ file.name }}
                </p>
                <div>
                    <table style="width:100%">
                        <tr>
                            <td class="leftTd">名称: </td>
                            <td>
                                <p class="detail"> {{ file.name }}</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="leftTd">路径: </td>
                            <td>
                                <p class="detail"> {{ file.path }}</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="leftTd">时间: </td>
                            <td>
                                <p class="detail"> {{ file.createTime }}</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="leftTd">大小: </td>
                            <td>
                                <p class="detail"> {{ file.size }}</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="leftTd"><Button type="primary" size="large" style="margin-right:20px"
                                    @click="download">下载</Button> </td>
                            <td>
                                <p class="detail"> <Button type="error" size="large" style="margin-right:20px"
                                        @click="modal1 = true">删除</Button></p>
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
import axios from 'axios';
export default {
    name: 'FileContent',
    props: ['back'],
    data() {
        return {
            file: {
            },
            modal1: false
        }
    },
    methods: {
        ok() {
            axios.get('/delFile?fileId=' + this.file.id).then(() => {
                this.$Message.info("已删除");
                this.back('fileList')
            }).catch(() => {

                this.$Message.info("删除失败");
            })
        },
        cancel() {
            this.$Message.info('取消删除');
        },
        download() {
            axios.get('/download/' + this.file.genPath, { responseType: 'blob' }).then(res => {
                // // new Blob([data])用来创建URL的file对象或者blob对象
                // let url = window.URL.createObjectURL(new Blob([res.data]));
                // // 生成一个a标签
                // let link = document.createElement("a");
                // link.style.display = "none";
                // link.href = url;
                // // 生成时间戳
                // //let timestamp = new Date().getTime();
                // link.download = this.file.name
                // document.body.appendChild(link);
                // link.click();
                const blob = new Blob([res.data]);//处理文档流
                const fileName = this.file.name;
                const elink = document.createElement('a');
                elink.download = fileName;
                elink.style.display = 'none';
                elink.href = URL.createObjectURL(blob);
                document.body.appendChild(elink);
                elink.click();
                URL.revokeObjectURL(elink.href); // 释放URL 对象
                document.body.removeChild(elink);
            })
        },
    },
    mounted() {
        this.file = JSON.parse(sessionStorage.getItem('currentFile'))
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