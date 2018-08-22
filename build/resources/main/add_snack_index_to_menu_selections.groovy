databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1534886452307-1") {
        addColumn(tableName: "menu_selection") {
            column(name: "snack_index", type: "integer") {
                constraints(nullable: "false")
            }
        }
    }
}
