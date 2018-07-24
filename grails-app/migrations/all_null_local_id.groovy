databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1532011356032-1") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "local_id", tableName: "menu")
    }

    changeSet(author: "joel (generated)", id: "1532011356032-2") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "local_id", tableName: "menu_item")
    }

    changeSet(author: "joel (generated)", id: "1532011356032-3") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "local_id", tableName: "menu_item_option")
    }

    changeSet(author: "joel (generated)", id: "1532011356032-4") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "local_id", tableName: "menu_item_option_group")
    }

    changeSet(author: "joel (generated)", id: "1532011356032-5") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "local_id", tableName: "menu_section")
    }
}
