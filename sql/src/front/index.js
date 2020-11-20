new Vue({
    el: "#app",
    data: {
        types: [
            {
              value: 'MySQL',
              label: 'MySQL'
            }, {
              value: 'GaussDB',
              label: 'GaussDB'
            }
        ],
        type: "MySQL",
        body: {
            type: "MySQL",
            count: "",
            record: "",
            ip: "",
            port: "",
            username: "",
            password: "",
            table: "",
            database: ""
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
        }

    }
})