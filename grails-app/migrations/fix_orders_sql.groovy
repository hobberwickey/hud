databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1532726800959-1") {
        createTable(tableName: "menu_item_option_menu_selections") {
            column(name: "menu_selection_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_item_option_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1532726800959-2") {
        createTable(tableName: "menu_selection_menu_item_options") {
            column(name: "menu_item_option_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_selection_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1532726800959-3") {
        addColumn(tableName: "menu_selection") {
            column(name: "menu_item_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1532726800959-4") {
        addColumn(tableName: "menu_selection") {
            column(name: "orders_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1532726800959-5") {
        addForeignKeyConstraint(baseColumnNames: "orders_id", baseTableName: "menu_selection", constraintName: "FK64tdjnva7uvosnai5v29bebuo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "orders")
    }

    changeSet(author: "joel (generated)", id: "1532726800959-6") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_id", baseTableName: "menu_selection", constraintName: "FK7t642evjigtnk219xnw7ehxt8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item")
    }

    changeSet(author: "joel (generated)", id: "1532726800959-7") {
        addForeignKeyConstraint(baseColumnNames: "menu_selection_id", baseTableName: "menu_selection_menu_item_options", constraintName: "FKeu4jp5r0s9s7ha8lohe07keni", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_selection")
    }

    changeSet(author: "joel (generated)", id: "1532726800959-8") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_option_id", baseTableName: "menu_item_option_menu_selections", constraintName: "FKks3tpwftdr9go9vft1tal4tm8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item_option")
    }

    changeSet(author: "joel (generated)", id: "1532726800959-9") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_option_id", baseTableName: "menu_selection_menu_item_options", constraintName: "FKn47nuu07v7l7oxj8yc1ekbfgc", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item_option")
    }

    changeSet(author: "joel (generated)", id: "1532726800959-10") {
        addForeignKeyConstraint(baseColumnNames: "menu_selection_id", baseTableName: "menu_item_option_menu_selections", constraintName: "FKq6cjbxjhf0oggn6xa9drgitmj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_selection")
    }

    changeSet(author: "joel (generated)", id: "1532726800959-11") {
        dropForeignKeyConstraint(baseTableName: "orders_menu_selection", constraintName: "FK8cj9mqtqqext76i9ukinmfj4x")
    }

    changeSet(author: "joel (generated)", id: "1532726800959-12") {
        dropForeignKeyConstraint(baseTableName: "menu_item_option_menu_selection", constraintName: "FKluyxq15wlwurld8bnm2dimjij")
    }

    changeSet(author: "joel (generated)", id: "1532726800959-13") {
        dropForeignKeyConstraint(baseTableName: "menu_item_menu_selection", constraintName: "FKng33uiheh82id7ct93tia0q18")
    }

    changeSet(author: "joel (generated)", id: "1532726800959-14") {
        dropForeignKeyConstraint(baseTableName: "orders_menu_selection", constraintName: "FKpkybpshw4wc6n0lck4qi3ie2q")
    }

    changeSet(author: "joel (generated)", id: "1532726800959-15") {
        dropForeignKeyConstraint(baseTableName: "menu_item_menu_selection", constraintName: "FKrtg67hpu2yghkyo44b3r2rth4")
    }

    changeSet(author: "joel (generated)", id: "1532726800959-16") {
        dropForeignKeyConstraint(baseTableName: "menu_item_option_menu_selection", constraintName: "FKsteovqebe7km8u285s0ii7xju")
    }

    changeSet(author: "joel (generated)", id: "1532726800959-17") {
        dropTable(tableName: "menu_item_menu_selection")
    }

    changeSet(author: "joel (generated)", id: "1532726800959-18") {
        dropTable(tableName: "menu_item_option_menu_selection")
    }

    changeSet(author: "joel (generated)", id: "1532726800959-19") {
        dropTable(tableName: "orders_menu_selection")
    }
}
