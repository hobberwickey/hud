databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1532546925441-1") {
        addPrimaryKey(columnNames: "dining_hall_id, menu_id", tableName: "dining_hall_menus")
    }

    changeSet(author: "joel (generated)", id: "1532546925441-2") {
        dropForeignKeyConstraint(baseTableName: "menu_dining_halls", constraintName: "FK34vcrwwfhumdiwvmc43gu49nf")
    }

    changeSet(author: "joel (generated)", id: "1532546925441-3") {
        dropForeignKeyConstraint(baseTableName: "menu_dining_halls", constraintName: "FK8b1duwn7s0oksgka77u7dogl0")
    }

    changeSet(author: "joel (generated)", id: "1532546925441-4") {
        dropTable(tableName: "menu_dining_halls")
    }

    changeSet(author: "joel (generated)", id: "1532546925441-5") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "local_id", tableName: "menu_item")
    }

    changeSet(author: "joel (generated)", id: "1532546925441-6") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "local_id", tableName: "menu_item_option")
    }

    changeSet(author: "joel (generated)", id: "1532546925441-7") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "local_id", tableName: "menu_item_option_group")
    }

    changeSet(author: "joel (generated)", id: "1532546925441-8") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "local_id", tableName: "menu_section")
    }
}
