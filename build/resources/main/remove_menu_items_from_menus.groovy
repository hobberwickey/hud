databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1531844888855-1") {
        createTable(tableName: "menu_item_menu") {
            column(name: "menu_item_menus_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1531844888855-2") {
        addForeignKeyConstraint(baseColumnNames: "menu_id", baseTableName: "menu_item_menu", constraintName: "FK5qijpthg9vcdg9klbn6t9h0qn", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu")
    }

    changeSet(author: "joel (generated)", id: "1531844888855-3") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_menus_id", baseTableName: "menu_item_menu", constraintName: "FKfxu6j2316xnncw7ufe1av09pv", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item")
    }

    changeSet(author: "joel (generated)", id: "1531844888855-4") {
        dropForeignKeyConstraint(baseTableName: "menu_menu_items", constraintName: "FK4x3jk87hut26y3nsyigal068m")
    }

    changeSet(author: "joel (generated)", id: "1531844888855-5") {
        dropForeignKeyConstraint(baseTableName: "menu_item_menus", constraintName: "FK5nvhv5h6uek01ljyp32xrgnvj")
    }

    changeSet(author: "joel (generated)", id: "1531844888855-6") {
        dropForeignKeyConstraint(baseTableName: "menu_item_menus", constraintName: "FKjldusxhyqgg9ntfstk9my4ffo")
    }

    changeSet(author: "joel (generated)", id: "1531844888855-7") {
        dropForeignKeyConstraint(baseTableName: "menu_menu_items", constraintName: "FKmdmsi5www0mc1g7w0sxjjwtle")
    }

    changeSet(author: "joel (generated)", id: "1531844888855-8") {
        dropTable(tableName: "menu_item_menus")
    }

    changeSet(author: "joel (generated)", id: "1531844888855-9") {
        dropTable(tableName: "menu_menu_items")
    }
}
