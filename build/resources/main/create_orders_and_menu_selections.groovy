databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1532445296394-1") {
        createTable(tableName: "dining_hall_order") {
            column(name: "dining_hall_orders_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "order_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1532445296394-2") {
        createTable(tableName: "menu_item_menu_selection") {
            column(name: "menu_item_menu_selections_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_selection_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1532445296394-3") {
        createTable(tableName: "menu_item_option_menu_selection") {
            column(name: "menu_item_option_menu_selections_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_selection_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1532445296394-4") {
        createTable(tableName: "menu_order") {
            column(name: "menu_orders_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "order_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1532445296394-5") {
        createTable(tableName: "menu_selection") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "menu_selectionPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1532445296394-6") {
        createTable(tableName: "order") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "orderPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "pickup_date", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "updated_at", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "repeated", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "parent_order_id", type: "BIGINT")

            column(name: "pickup_time", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "created_on", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1532445296394-7") {
        addForeignKeyConstraint(baseColumnNames: "menu_orders_id", baseTableName: "menu_order", constraintName: "FKa7wt3qsvnn4oisp8r9sjyurmo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu")
    }

    changeSet(author: "joel (generated)", id: "1532445296394-8") {
        addForeignKeyConstraint(baseColumnNames: "order_id", baseTableName: "dining_hall_order", constraintName: "FKdha39ri7ceie538n3pjsvyvmg", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "order")
    }

    changeSet(author: "joel (generated)", id: "1532445296394-9") {
        addForeignKeyConstraint(baseColumnNames: "dining_hall_orders_id", baseTableName: "dining_hall_order", constraintName: "FKg6b6vmlny2jvfdnxt0wrw6yt6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "dining_hall")
    }

    changeSet(author: "joel (generated)", id: "1532445296394-10") {
        addForeignKeyConstraint(baseColumnNames: "parent_order_id", baseTableName: "order", constraintName: "FKksxkpcdu51bgp6h1yr4g5nxoe", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "order")
    }

    changeSet(author: "joel (generated)", id: "1532445296394-11") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_option_menu_selections_id", baseTableName: "menu_item_option_menu_selection", constraintName: "FKluyxq15wlwurld8bnm2dimjij", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item_option")
    }

    changeSet(author: "joel (generated)", id: "1532445296394-12") {
        addForeignKeyConstraint(baseColumnNames: "order_id", baseTableName: "menu_order", constraintName: "FKmjo65y69f2rw1yrs423wf808b", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "order")
    }

    changeSet(author: "joel (generated)", id: "1532445296394-13") {
        addForeignKeyConstraint(baseColumnNames: "menu_selection_id", baseTableName: "menu_item_menu_selection", constraintName: "FKng33uiheh82id7ct93tia0q18", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_selection")
    }

    changeSet(author: "joel (generated)", id: "1532445296394-14") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_menu_selections_id", baseTableName: "menu_item_menu_selection", constraintName: "FKrtg67hpu2yghkyo44b3r2rth4", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item")
    }

    changeSet(author: "joel (generated)", id: "1532445296394-15") {
        addForeignKeyConstraint(baseColumnNames: "menu_selection_id", baseTableName: "menu_item_option_menu_selection", constraintName: "FKsteovqebe7km8u285s0ii7xju", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_selection")
    }

    changeSet(author: "joel (generated)", id: "1532445296394-16") {
        dropForeignKeyConstraint(baseTableName: "user_dining_hall", constraintName: "FKasjbotiocg4nf2m7m4e6e3j41")
    }

    changeSet(author: "joel (generated)", id: "1532445296394-17") {
        dropForeignKeyConstraint(baseTableName: "user_dining_hall", constraintName: "FKcmf6k78iv4i5dj9dbi96xpnif")
    }

    changeSet(author: "joel (generated)", id: "1532445296394-18") {
        dropTable(tableName: "user_dining_hall")
    }
}
