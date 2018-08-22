databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1531846825825-1") {
        addColumn(tableName: "menu") {
            column(name: "meal_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1531846825825-2") {
        addForeignKeyConstraint(baseColumnNames: "meal_id", baseTableName: "menu", constraintName: "FKh3xjvwgcdtm55im740supw0po", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "meal")
    }

    changeSet(author: "joel (generated)", id: "1531846825825-3") {
        dropForeignKeyConstraint(baseTableName: "meal_menu", constraintName: "FK5ywj42p0hun7ifyfd3p3pxxnt")
    }

    changeSet(author: "joel (generated)", id: "1531846825825-4") {
        dropForeignKeyConstraint(baseTableName: "meal_menu", constraintName: "FKkv7g07ebujbsa2ryic0anw8oa")
    }

    changeSet(author: "joel (generated)", id: "1531846825825-5") {
        dropTable(tableName: "meal_menu")
    }
}
