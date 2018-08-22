databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1530738675045-1") {
        createTable(tableName: "dining_hall") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "dining_hallPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "ordering", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "closing_date", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "opening_date", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1530738675045-2") {
        createTable(tableName: "dining_hall_menu") {
            column(name: "dining_hall_menus_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1530738675045-3") {
        createTable(tableName: "meal") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "mealPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1530738675045-4") {
        createTable(tableName: "meal_menus") {
            column(name: "menu_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "mm_meal_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1530738675045-5") {
        createTable(tableName: "menu") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "menuPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1530738675045-6") {
        createTable(tableName: "menu_item") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "menu_itemPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1530738675045-7") {
        createTable(tableName: "menu_item_menu_item_option") {
            column(name: "menu_item_menu_item_options_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_item_option_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1530738675045-8") {
        createTable(tableName: "menu_item_menus") {
            column(name: "menu_item_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1530738675045-9") {
        createTable(tableName: "menu_item_option") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "menu_item_optionPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1530738675045-10") {
        createTable(tableName: "menu_menu_items") {
            column(name: "menu_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_item_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1530738675045-11") {
        createTable(tableName: "mm_meal_menus") {
            column(name: "mm_meal_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1530738675045-12") {
        addPrimaryKey(columnNames: "menu_item_id, menu_id", tableName: "menu_item_menus")
    }

    changeSet(author: "joel (generated)", id: "1530738675045-13") {
        addPrimaryKey(columnNames: "menu_id, menu_item_id", tableName: "menu_menu_items")
    }

    changeSet(author: "joel (generated)", id: "1530738675045-14") {
        addPrimaryKey(columnNames: "mm_meal_id, menu_id", tableName: "mm_meal_menus")
    }

    changeSet(author: "joel (generated)", id: "1530738675045-15") {
        addForeignKeyConstraint(baseColumnNames: "menu_id", baseTableName: "meal_menus", constraintName: "FK1345dnuvx8qbbf8pgqvwm4vw5", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu")
    }

    changeSet(author: "joel (generated)", id: "1530738675045-16") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_menu_item_options_id", baseTableName: "menu_item_menu_item_option", constraintName: "FK2ypeq6wobc39sjqag47hwllcp", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item")
    }

    changeSet(author: "joel (generated)", id: "1530738675045-17") {
        addForeignKeyConstraint(baseColumnNames: "menu_id", baseTableName: "mm_meal_menus", constraintName: "FK4e074teckrxiteq16vckbugbb", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu")
    }

    changeSet(author: "joel (generated)", id: "1530738675045-18") {
        addForeignKeyConstraint(baseColumnNames: "menu_id", baseTableName: "menu_menu_items", constraintName: "FK4x3jk87hut26y3nsyigal068m", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu")
    }

    changeSet(author: "joel (generated)", id: "1530738675045-19") {
        addForeignKeyConstraint(baseColumnNames: "menu_id", baseTableName: "menu_item_menus", constraintName: "FK5nvhv5h6uek01ljyp32xrgnvj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu")
    }

    changeSet(author: "joel (generated)", id: "1530738675045-20") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_option_id", baseTableName: "menu_item_menu_item_option", constraintName: "FK96dny0d3hh3wtu31824t29dyg", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item_option")
    }

    changeSet(author: "joel (generated)", id: "1530738675045-21") {
        addForeignKeyConstraint(baseColumnNames: "dining_hall_menus_id", baseTableName: "dining_hall_menu", constraintName: "FKhgs2il149ncebkmlhvdhmc302", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "dining_hall")
    }

    changeSet(author: "joel (generated)", id: "1530738675045-22") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_id", baseTableName: "menu_item_menus", constraintName: "FKjldusxhyqgg9ntfstk9my4ffo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item")
    }

    changeSet(author: "joel (generated)", id: "1530738675045-23") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_id", baseTableName: "menu_menu_items", constraintName: "FKmdmsi5www0mc1g7w0sxjjwtle", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item")
    }

    changeSet(author: "joel (generated)", id: "1530738675045-24") {
        addForeignKeyConstraint(baseColumnNames: "mm_meal_id", baseTableName: "mm_meal_menus", constraintName: "FKn9h4b4h9mwsaw44u6w3k9qdih", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "meal")
    }

    changeSet(author: "joel (generated)", id: "1530738675045-25") {
        addForeignKeyConstraint(baseColumnNames: "menu_id", baseTableName: "dining_hall_menu", constraintName: "FKo951h7ql37432a92nfetbhm02", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu")
    }

    changeSet(author: "joel (generated)", id: "1530738675045-26") {
        addForeignKeyConstraint(baseColumnNames: "mm_meal_id", baseTableName: "meal_menus", constraintName: "FKrnaxwcym1mukes2031sgmdlsi", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "meal")
    }
}
