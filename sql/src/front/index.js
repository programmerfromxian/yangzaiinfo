new Vue({
    el: "#app",
    data: {
        restaurants: [],
        columns: "",
        types: [
            {
              value: 'MySQL',
              label: 'MySQL'
            }, {
              value: 'GaussDB',
              label: 'GaussDB'
            }
        ],
        body: {
            type: "MySQL",
            count: "30",
            record: "1,2,3",
            ip: "127.0.0.1",
            port: "3306",
            username: "root",
            password: "000000",
            table: "user",
            database: "test",
        }
    },
    created: function () {
         this.changeType("MySQL")
    },
    methods: {
        changeType: function(type) {
            _this = this;
            axios({
                method: "GET",
                url: URL.SQL.LOAD_PARAM,
                withCredentials: true,
                params: {
                    type: type
                },
            })
                .then(result => {
                    if (result.data.success === true) {
                        _this.body = result.data.returnData
                    } else {
                        this.$message.error(result.data.errMsg)
                    }
                })
        },
        // 新增
        insert: function () {
            axios({
                method: "POST",
                url: URL.SQL.INSERT,
                data: this.body,
            })
                .then(result => {
                    if (result.data.success === true) {
                        _this.$notify({
                            title: '结果',
                            message: result.data.returnData,
                            duration: 0
                    });
                    } else {
                        this.$message.error(result.data.errMsg)
                    }
                })
        },
        // 新增
        getColumns: function () {
            _this = this;
            axios({
                method: "POST",
                url: URL.SQL.COLUMNS,
                data: this.body,
            })
                .then(result => {
                    if (result.data.success === true) {
                        _this.columns = result.data.returnData
                    } else {
                        this.$message.error(result.data.errMsg)
                    }
                })
        }

    }
})