databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1531414400714-1") {
        createTable(tableName: "meal_menu") {
            column(name: "meal_menus_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1531414400714-2") {
        createTable(tableName: "menu_item_menu_item_option_group") {
            column(name: "menu_item_menu_item_option_groups_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_item_option_group_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1531414400714-3") {
        createTable(tableName: "menu_item_option_group") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "menu_item_option_groupPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "ordering", type: "VARCHAR(255)")

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1531414400714-4") {
        createTable(tableName: "menu_item_option_group_menu_item_option") {
            column(name: "menu_item_option_group_menu_item_options_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_item_option_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1531414400714-5") {
        addColumn(tableName: "menu_item_option") {
            column(name: "type", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1531414400714-6") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_option_id", baseTableName: "menu_item_option_group_menu_item_option", constraintName: "FK2uwmyectb0rmg4mcld5m4x2yx", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item_option")
    }

    changeSet(author: "joel (generated)", id: "1531414400714-7") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_menu_item_option_groups_id", baseTableName: "menu_item_menu_item_option_group", constraintName: "FK37n9cg3hng8r82r0364fiixh6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item")
    }

    changeSet(author: "joel (generated)", id: "1531414400714-8") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_option_group_menu_item_options_id", baseTableName: "menu_item_option_group_menu_item_option", constraintName: "FK3v9q3tmph6bwulqmpdhj0iota", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item_option_group")
    }

    changeSet(author: "joel (generated)", id: "1531414400714-9") {
        addForeignKeyConstraint(baseColumnNames: "menu_id", baseTableName: "meal_menu", constraintName: "FK5ywj42p0hun7ifyfd3p3pxxnt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu")
    }

    changeSet(author: "joel (generated)", id: "1531414400714-10") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_option_group_id", baseTableName: "menu_item_menu_item_option_group", constraintName: "FKf28yy9jo962n6pdbcpvswm2lk", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item_option_group")
    }

    changeSet(author: "joel (generated)", id: "1531414400714-11") {
        addForeignKeyConstraint(baseColumnNames: "meal_menus_id", baseTableName: "meal_menu", constraintName: "FKkv7g07ebujbsa2ryic0anw8oa", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "meal")
    }

    changeSet(author: "joel (generated)", id: "1531414400714-12") {
        dropForeignKeyConstraint(baseTableName: "meal_menus", constraintName: "FK1345dnuvx8qbbf8pgqvwm4vw5")
    }

    changeSet(author: "joel (generated)", id: "1531414400714-13") {
        dropForeignKeyConstraint(baseTableName: "menu_item_menu_item_option", constraintName: "FK2ypeq6wobc39sjqag47hwllcp")
    }

    changeSet(author: "joel (generated)", id: "1531414400714-14") {
        dropForeignKeyConstraint(baseTableName: "mm_meal_menus", constraintName: "FK4e074teckrxiteq16vckbugbb")
    }

    changeSet(author: "joel (generated)", id: "1531414400714-15") {
        dropForeignKeyConstraint(baseTableName: "menu_item_menu_item_option", constraintName: "FK96dny0d3hh3wtu31824t29dyg")
    }

    changeSet(author: "joel (generated)", id: "1531414400714-16") {
        dropForeignKeyConstraint(baseTableName: "mm_meal_menus", constraintName: "FKn9h4b4h9mwsaw44u6w3k9qdih")
    }

    changeSet(author: "joel (generated)", id: "1531414400714-17") {
        dropForeignKeyConstraint(baseTableName: "meal_menus", constraintName: "FKrnaxwcym1mukes2031sgmdlsi")
    }

    changeSet(author: "joel (generated)", id: "1531414400714-18") {
        dropTable(tableName: "meal_menus")
    }

    changeSet(author: "joel (generated)", id: "1531414400714-19") {
        dropTable(tableName: "menu_item_menu_item_option")
    }

    changeSet(author: "joel (generated)", id: "1531414400714-20") {
        dropTable(tableName: "mm_meal_menus")
    }

    changeSet(author: "joel (generated)", id: "1531414400714-21") {
        addDefaultValue(columnDataType: "boolean", columnName: "status", defaultValueBoolean: "true", tableName: "dining_hall")
    }
}
