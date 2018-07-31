databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1532870721081-1") {
        addColumn(tableName: "menu_item") {
            column(name: "position", type: "integer") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1532870721081-2") {
        addColumn(tableName: "menu_item_option") {
            column(name: "position", type: "integer") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1532870721081-3") {
        addColumn(tableName: "menu_section") {
            column(name: "position", type: "integer") {
                constraints(nullable: "false")
            }
        }
    }
}
