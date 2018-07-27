databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1532545391590-1") {
        createTable(tableName: "dining_hall_menus") {
            column(name: "menu_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "dining_hall_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1532545391590-2") {
        createTable(tableName: "menu_dining_halls") {
            column(name: "dining_hall_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1532545391590-3") {
        addForeignKeyConstraint(baseColumnNames: "dining_hall_id", baseTableName: "menu_dining_halls", constraintName: "FK34vcrwwfhumdiwvmc43gu49nf", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "dining_hall")
    }

    changeSet(author: "joel (generated)", id: "1532545391590-4") {
        addForeignKeyConstraint(baseColumnNames: "menu_id", baseTableName: "menu_dining_halls", constraintName: "FK8b1duwn7s0oksgka77u7dogl0", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu")
    }

    changeSet(author: "joel (generated)", id: "1532545391590-5") {
        addForeignKeyConstraint(baseColumnNames: "menu_id", baseTableName: "dining_hall_menus", constraintName: "FKdqo04g692619tn14qdksof15y", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu")
    }

    changeSet(author: "joel (generated)", id: "1532545391590-6") {
        addForeignKeyConstraint(baseColumnNames: "dining_hall_id", baseTableName: "dining_hall_menus", constraintName: "FKe3q7cjtsm903gqi2d9ehe8g5w", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "dining_hall")
    }

    changeSet(author: "joel (generated)", id: "1532545391590-7") {
        dropForeignKeyConstraint(baseTableName: "dining_hall_menu", constraintName: "FKhgs2il149ncebkmlhvdhmc302")
    }

    changeSet(author: "joel (generated)", id: "1532545391590-8") {
        dropForeignKeyConstraint(baseTableName: "dining_hall_menu", constraintName: "FKo951h7ql37432a92nfetbhm02")
    }

    changeSet(author: "joel (generated)", id: "1532545391590-9") {
        dropTable(tableName: "dining_hall_menu")
    }
}
