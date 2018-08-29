databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1535575546244-1") {
        addColumn(tableName: "menu_selection") {
            column(name: "order_index", type: "integer") {
                constraints(nullable: "false")
            }
        }
    }
}
