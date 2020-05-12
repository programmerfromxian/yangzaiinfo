var basePath = "http://localhost:9090/information/";
var installPath = "http://localhost:9090/"
var URL = {
    INDEX_INIT: {
        INIT: this.basePath + "queryAll",
    },
    INFORMATION: {
        CREATE: this.basePath + "add",
        EDIT: this.basePath + "edit",
        DELETE: this.basePath + "delete",
    },
    INSTALL: {
        INSTALL: this.installPath + "install"
    }
};