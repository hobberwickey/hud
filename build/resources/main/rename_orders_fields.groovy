databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1534914807457-1") {
        createTable(tableName: "orders_order_pickup") {
            column(name: "orders_order_pickups_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "order_pickup_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1534914807457-2") {
        addForeignKeyConstraint(baseColumnNames: "orders_order_pickups_id", baseTableName: "orders_order_pickup", constraintName: "FKg4mmposjwueui3ed2l14evo3g", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "orders")
    }

    changeSet(author: "joel (generated)", id: "1534914807457-3") {
        addForeignKeyConstraint(baseColumnNames: "order_pickup_id", baseTableName: "orders_order_pickup", constraintName: "FKixqthtk7q7939c1ucwfcfyf8a", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "order_pickup")
    }

    changeSet(author: "joel (generated)", id: "1534914807457-4") {
        dropForeignKeyConstraint(baseTableName: "order_pickup", constraintName: "FKb8ulily91aov0typdxu0eyivm")
    }

    changeSet(author: "joel (generated)", id: "1534914807457-5") {
        dropColumn(columnName: "purchase_id", tableName: "order_pickup")
    }
}
