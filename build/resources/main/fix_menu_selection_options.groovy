databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1534439559305-1") {
        addPrimaryKey(columnNames: "menu_item_option_id, menu_selection_id", tableName: "menu_item_option_menu_selections")
    }

    changeSet(author: "joel (generated)", id: "1534439559305-2") {
        addPrimaryKey(columnNames: "menu_selection_id, menu_item_option_id", tableName: "menu_selection_menu_item_options")
    }
}
