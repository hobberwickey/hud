databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1532700657963-1") {
        createTable(tableName: "orders") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "ordersPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "updated_at", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "menu_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "dining_hall_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "created_on", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1532700657963-2") {
        createTable(tableName: "orders_menu_selection") {
            column(name: "orders_menu_selections_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_selection_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1532700657963-3") {
        createTable(tableName: "orders_order_pickup") {
            column(name: "orders_order_pickups_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "order_pickup_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1532700657963-4") {
        addForeignKeyConstraint(baseColumnNames: "menu_id", baseTableName: "orders", constraintName: "FK1nojj2acwdssvxe1dnrkrmmed", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu")
    }

    changeSet(author: "joel (generated)", id: "1532700657963-5") {
        addForeignKeyConstraint(baseColumnNames: "orders_menu_selections_id", baseTableName: "orders_menu_selection", constraintName: "FK8cj9mqtqqext76i9ukinmfj4x", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "orders")
    }

    changeSet(author: "joel (generated)", id: "1532700657963-6") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "orders", constraintName: "FKel9kyl84ego2otj2accfd8mr7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user")
    }

    changeSet(author: "joel (generated)", id: "1532700657963-7") {
        addForeignKeyConstraint(baseColumnNames: "orders_order_pickups_id", baseTableName: "orders_order_pickup", constraintName: "FKg4mmposjwueui3ed2l14evo3g", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "orders")
    }

    changeSet(author: "joel (generated)", id: "1532700657963-8") {
        addForeignKeyConstraint(baseColumnNames: "order_pickup_id", baseTableName: "orders_order_pickup", constraintName: "FKixqthtk7q7939c1ucwfcfyf8a", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "order_pickup")
    }

    changeSet(author: "joel (generated)", id: "1532700657963-9") {
        addForeignKeyConstraint(baseColumnNames: "menu_selection_id", baseTableName: "orders_menu_selection", constraintName: "FKpkybpshw4wc6n0lck4qi3ie2q", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_selection")
    }

    changeSet(author: "joel (generated)", id: "1532700657963-10") {
        addForeignKeyConstraint(baseColumnNames: "dining_hall_id", baseTableName: "orders", constraintName: "FKr7ia8y4x166vdwg93q9nfvefr", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "dining_hall")
    }

    changeSet(author: "joel (generated)", id: "1532700657963-11") {
        dropForeignKeyConstraint(baseTableName: "menu_order", constraintName: "FKa7wt3qsvnn4oisp8r9sjyurmo")
    }

    changeSet(author: "joel (generated)", id: "1532700657963-12") {
        dropForeignKeyConstraint(baseTableName: "dining_hall_order", constraintName: "FKdha39ri7ceie538n3pjsvyvmg")
    }

    changeSet(author: "joel (generated)", id: "1532700657963-13") {
        dropForeignKeyConstraint(baseTableName: "dining_hall_order", constraintName: "FKg6b6vmlny2jvfdnxt0wrw6yt6")
    }

    changeSet(author: "joel (generated)", id: "1532700657963-14") {
        dropForeignKeyConstraint(baseTableName: "menu_order", constraintName: "FKmjo65y69f2rw1yrs423wf808b")
    }

    changeSet(author: "joel (generated)", id: "1532700657963-15") {
        dropTable(tableName: "dining_hall_order")
    }

    changeSet(author: "joel (generated)", id: "1532700657963-16") {
        dropTable(tableName: "menu_order")
    }

    changeSet(author: "joel (generated)", id: "1532700657963-17") {
        dropTable(tableName: "order")
    }
}
