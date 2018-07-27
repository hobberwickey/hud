databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1531852936895-1") {
        addColumn(tableName: "menu") {
            column(name: "local_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1531852936895-2") {
        addColumn(tableName: "menu_item") {
            column(name: "local_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1531852936895-3") {
        addColumn(tableName: "menu_item_option") {
            column(name: "local_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1531852936895-4") {
        addColumn(tableName: "menu_item_option_group") {
            column(name: "local_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1531852936895-5") {
        addColumn(tableName: "menu_section") {
            column(name: "local_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }
}
