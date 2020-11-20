new Vue({
    el: "#app",
    data: {
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
    methods: {
        // 新增
        insert: function () {
            axios({
                method: "POST",
                url: URL.SQL.INSERT,
                data: this.body,
            })
                .then(result => {
                    if (result.data.success === true) {
                        this.$message.success(result.data.returnData)
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
                        console.log(result.data);
                        _this.columns = result.data.returnData
                    } else {
                        this.$message.error(result.data.errMsg)
                    }
                })
        }

    }
})