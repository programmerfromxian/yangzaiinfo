new Vue({
    el: "#app",
    data: {
        destroyOnClose: true,
        informationList: [],
        pagination: {
            queryString: ""
        },
        createDialogVisible: false,
        editDialogVisible: false,
        createInfo: {
            type: "",
            name: "",
            account: "",
            password: "",
            description: "",
            url: ""
        },
        editInfo: {
            type: "",
            name: "",
            account: "",
            password: "",
            description: "",
            url: ""
        }
    },
    created: function () {
        this.indexInit();
    },
    methods: {
        // 主页初始化
        indexInit: function () {
            axios({
                method: "GET",
                url: URL.INDEX_INIT.INIT,
                params: {},
                withCredentials: true
            })
                .then(result => {
                    if (result.data.success === true) {
                        this.informationList = result.data.returnData;
                    } else {
                        this.$message.error(result.data.errMsg)
                    }
                })
        },
        // 分页条件查询
        findPageByCondition: function () {

        },
        // 显示新增信息模态框
        visibleCreateDialog: function () {
            this.createDialogVisible = true;
        },
        // 新增
        handleCreate: function () {
            axios({
                method: "POST",
                url: URL.INFORMATION.CREATE,
                data: this.createInfo,
            })
                .then(result => {
                    if (result.data.success === true) {
                        this.createDialogVisible = false;
                        this.$message.success(result.data.returnData)
                        this.indexInit()
                    } else {
                        this.$message.error(result.data.errMsg)
                    }
                })
        },
        // 修改模态框
        visibleEditDialog: function (information) {
            this.editInfo = information;
            this.editDialogVisible = true;
        },
        // 删除
        handleDel: function (information) {
            axios({
                method: "POST",
                url: URL.INFORMATION.DELETE,
                data: information
            })
                .then(result => {
                    if (result.data.success === true) {
                        this.$message.success(result.data.returnData);
                        this.editDialogVisible = false;
                        this.indexInit();
                    } else {
                        this.$message.error(result.data.errMsg);
                    }
                })
        },
        handleEdit: function () {
            axios({
                method: "POST",
                url: URL.INFORMATION.EDIT,
                data: this.editInfo
            })
                .then(result => {
                    if (result.data.success === true) {
                        this.$message.success(result.data.returnData);
                        this.editDialogVisible = false;
                        this.indexInit();
                    } else {
                        this.$message.error(result.data.errMsg);
                    }
                })
        }

    }
})