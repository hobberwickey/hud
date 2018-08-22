databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1534784335309-1") {
        addColumn(tableName: "dining_hall") {
            column(name: "deleted", type: "bit") {
                constraints(nullable: "false")
            }
        }
    }
}
