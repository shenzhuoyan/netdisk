<template>
    <div>
        <div style="background:#eee;padding: 20px">
            <Card :bordered="false">
                <p slot="title">
                    <Icon type="ios-arrow-back" size="25" @click="back('fileList')"></Icon>上传文件
                </p>
                <div>
                    <Upload type="drag" action="abc" :before-upload="uploadSectionFile">
                        <div style="padding: 20px 0">
                            <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                            <p>点击或将文件拖拽到这里上传</p>
                        </div>
                    </Upload>
                </div>

            </Card>
        </div>
        <Modal v-model="modal1" title="确认上传？" @on-ok="ok" @on-cancel="cancel">
            <p>确认上传文件</p>
        </Modal>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    name: 'UploadPage',
    props: ['back'],
    data() {
        return {
            path: '',
            modal1: false,
            currentDir: {}

        }
    },
    methods: {
        ok() {

        },
        cancel() {
            this.$Message.info('取消上传');
        },
        uploadSectionFile(file) {
            let formData = new FormData()
            formData.append('file', file)
            formData.append('parentId', this.currentDir.id)
            let config = {
                headers: { "Content-Type": "multipart/form-data" }
            };
            axios({
                url: '/addFile',
                method: 'POST',
                data: formData,
                config: config
            }).catch(err => {
                console.log(err)
            })
            this.back('fileList')
        },
    },
    mounted() {
        this.currentDir = JSON.parse(sessionStorage.getItem("currentDir"))
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