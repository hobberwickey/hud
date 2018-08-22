databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1530833891807-1") {
        addColumn(tableName: "menu") {
            column(name: "ordering", type: "varchar(255)")
        }
    }
}
