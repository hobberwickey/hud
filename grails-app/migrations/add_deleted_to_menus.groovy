databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1533744483371-1") {
        addColumn(tableName: "menu") {
            column(name: "deleted", type: "bit") {
                constraints(nullable: "false")
            }
        }
    }
}
