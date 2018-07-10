databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1531161981923-1") {
        createTable(tableName: "user") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "userPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "first_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "huid", type: "INT") {
                constraints(nullable: "false")
            }

            column(defaultValueBoolean: "true", name: "active", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "user_type", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "blocked", type: "BIT") {
                constraints(nullable: "false")
            }

            column(name: "last_name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "email", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1531161981923-2") {
        createTable(tableName: "user_dining_hall") {
            column(name: "user_dining_halls_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "dining_hall_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1531161981923-4") {
        addForeignKeyConstraint(baseColumnNames: "dining_hall_id", baseTableName: "user_dining_hall", constraintName: "FKasjbotiocg4nf2m7m4e6e3j41", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "dining_hall")
    }

    changeSet(author: "joel (generated)", id: "1531161981923-5") {
        addForeignKeyConstraint(baseColumnNames: "user_dining_halls_id", baseTableName: "user_dining_hall", constraintName: "FKcmf6k78iv4i5dj9dbi96xpnif", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user")
    }
}
