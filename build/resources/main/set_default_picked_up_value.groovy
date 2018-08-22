databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1534856639042-1") {
        addDefaultValue(columnDataType: "boolean", columnName: "picked_up", defaultValueBoolean: "true", tableName: "order_pickup")
    }
}
