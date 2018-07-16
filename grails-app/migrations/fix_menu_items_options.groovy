databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1531428866198-1") {
        createTable(tableName: "menu_item_option_group_menu_item_options") {
            column(name: "menu_item_option_group_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_item_option_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1531428866198-2") {
        createTable(tableName: "menu_item_option_menu_item_option_groups") {
            column(name: "menu_item_option_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_item_option_group_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1531428866198-3") {
        addPrimaryKey(columnNames: "menu_item_option_group_id, menu_item_option_id", tableName: "menu_item_option_group_menu_item_options")
    }

    changeSet(author: "joel (generated)", id: "1531428866198-4") {
        addPrimaryKey(columnNames: "menu_item_option_id, menu_item_option_group_id", tableName: "menu_item_option_menu_item_option_groups")
    }

    changeSet(author: "joel (generated)", id: "1531428866198-5") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_option_id", baseTableName: "menu_item_option_menu_item_option_groups", constraintName: "FK2tppmjbl936ladfbvwawfuhff", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item_option")
    }

    changeSet(author: "joel (generated)", id: "1531428866198-6") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_option_group_id", baseTableName: "menu_item_option_group_menu_item_options", constraintName: "FK3rhq5t0igj4ugvckochwb458t", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item_option_group")
    }

    changeSet(author: "joel (generated)", id: "1531428866198-7") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_option_id", baseTableName: "menu_item_option_group_menu_item_options", constraintName: "FK9j5rimm60119qnts73bjxygee", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item_option")
    }

    changeSet(author: "joel (generated)", id: "1531428866198-8") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_option_group_id", baseTableName: "menu_item_option_menu_item_option_groups", constraintName: "FKukt015qfqy7fykaad3pbjek8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item_option_group")
    }

    changeSet(author: "joel (generated)", id: "1531428866198-9") {
        dropForeignKeyConstraint(baseTableName: "menu_item_option_group_menu_item_option", constraintName: "FK2uwmyectb0rmg4mcld5m4x2yx")
    }

    changeSet(author: "joel (generated)", id: "1531428866198-10") {
        dropForeignKeyConstraint(baseTableName: "menu_item_option_group_menu_item_option", constraintName: "FK3v9q3tmph6bwulqmpdhj0iota")
    }

    changeSet(author: "joel (generated)", id: "1531428866198-11") {
        dropTable(tableName: "menu_item_option_group_menu_item_option")
    }
}
