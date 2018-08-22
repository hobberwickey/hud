databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1534916739236-1") {
        addColumn(tableName: "order_pickup") {
            column(name: "orders_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1534916739236-2") {
        addForeignKeyConstraint(baseColumnNames: "orders_id", baseTableName: "order_pickup", constraintName: "FKbqnhgtr4n1e7mpuaqk2ia9ytt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "orders")
    }

    changeSet(author: "joel (generated)", id: "1534916739236-3") {
        dropForeignKeyConstraint(baseTableName: "orders_order_pickup", constraintName: "FKg4mmposjwueui3ed2l14evo3g")
    }

    changeSet(author: "joel (generated)", id: "1534916739236-4") {
        dropForeignKeyConstraint(baseTableName: "orders_order_pickup", constraintName: "FKixqthtk7q7939c1ucwfcfyf8a")
    }

    changeSet(author: "joel (generated)", id: "1534916739236-5") {
        dropTable(tableName: "orders_order_pickup")
    }
}
