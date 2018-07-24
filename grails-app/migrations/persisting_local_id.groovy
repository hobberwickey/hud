databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1531852936895-1") {
        addColumn(tableName: "menu_section") {
            column(name: "local_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }
}
