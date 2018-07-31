databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1533052180128-1") {
        createTable(tableName: "user_dining_hall") {
            column(name: "user_dining_halls_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "dining_hall_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1533052180128-2") {
        addForeignKeyConstraint(baseColumnNames: "dining_hall_id", baseTableName: "user_dining_hall", constraintName: "FKasjbotiocg4nf2m7m4e6e3j41", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "dining_hall")
    }

    changeSet(author: "joel (generated)", id: "1533052180128-3") {
        addForeignKeyConstraint(baseColumnNames: "user_dining_halls_id", baseTableName: "user_dining_hall", constraintName: "FKcmf6k78iv4i5dj9dbi96xpnif", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user")
    }
}
