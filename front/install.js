new Vue({
    el: "#app",
    data: {
        installInfo: {
            ip: "",
            port: "22",
            username: "root",
            password: "",
            ip1: "",
            port1: "",
            ip2: "",
            port2: "",
            name: "",
            id: "",
            sftpUsername: "root",
            sftpPassword: "",
            filePath: "C:\Users\Administrator\Downloads\log.tar.gz",
            param1: "",
            param2: "",
            param3: ""
        }
    },
    created: function () {
    },
    methods: {
        // 新增
        handleInstall: function () {
            axios({
                method: "POST",
                url: URL.INSTALL.INSTALL,
                data: this.installInfo,
            })
                .then(result => {
                    if (result.data.success === true) {
                        this.$message.success(result.data.returnData)
                    } else {
                        this.$message.error(result.data.errMsg)
                    }
                })
        }
    }
})