databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1534342247836-1") {
        addColumn(tableName: "orders") {
            column(name: "canceled_on", type: "datetime")
        }
    }
}
