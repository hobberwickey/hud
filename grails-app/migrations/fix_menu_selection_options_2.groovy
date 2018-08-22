databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1534883787795-1") {
        dropForeignKeyConstraint(baseTableName: "menu_item_option_menu_selections", constraintName: "FKks3tpwftdr9go9vft1tal4tm8")
    }

    changeSet(author: "joel (generated)", id: "1534883787795-2") {
        dropForeignKeyConstraint(baseTableName: "menu_item_option_menu_selections", constraintName: "FKq6cjbxjhf0oggn6xa9drgitmj")
    }

    changeSet(author: "joel (generated)", id: "1534883787795-3") {
        dropTable(tableName: "menu_item_option_menu_selections")
    }
}
