databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1532623992652-1") {
        createTable(tableName: "order_pickup") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "order_pickupPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "picked_up", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "pickup_date", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "pickup_time", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1532623992652-2") {
        dropForeignKeyConstraint(baseTableName: "order", constraintName: "FKksxkpcdu51bgp6h1yr4g5nxoe")
    }

    changeSet(author: "joel (generated)", id: "1532623992652-3") {
        dropColumn(columnName: "parent_order_id", tableName: "order")
    }

    changeSet(author: "joel (generated)", id: "1532623992652-4") {
        dropColumn(columnName: "pickup_date", tableName: "order")
    }

    changeSet(author: "joel (generated)", id: "1532623992652-5") {
        dropColumn(columnName: "pickup_time", tableName: "order")
    }

    changeSet(author: "joel (generated)", id: "1532623992652-6") {
        dropColumn(columnName: "repeated", tableName: "order")
    }
}
