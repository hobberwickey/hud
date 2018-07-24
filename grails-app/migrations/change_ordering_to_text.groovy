databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1532013875619-1") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "local_id", tableName: "menu_section")
    }

    changeSet(author: "joel (generated)", id: "1532013875619-2") {
        modifyDataType(tableName: "menu_section", columnName: "ordering", newDataType: "longtext")
    }
}
