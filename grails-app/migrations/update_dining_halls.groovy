databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1531237983310-1") {
        addColumn(tableName: "dining_hall") {
            column(name: "status", type: "bit") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1531237983310-2") {
        dropColumn(columnName: "ordering", tableName: "dining_hall")
    }
}
