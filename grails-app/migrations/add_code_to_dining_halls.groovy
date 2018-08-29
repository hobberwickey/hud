databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1535484600670-1") {
        addColumn(tableName: "dining_hall") {
            column(name: "code", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }
}
