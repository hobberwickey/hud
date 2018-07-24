databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1531847442702-1") {
        createTable(tableName: "menu_section_menu_item") {
            column(name: "menu_section_menu_items_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_item_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1531847442702-2") {
        addForeignKeyConstraint(baseColumnNames: "menu_section_menu_items_id", baseTableName: "menu_section_menu_item", constraintName: "FKbsfo9dedra959su7hwavea7ol", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_section")
    }

    changeSet(author: "joel (generated)", id: "1531847442702-3") {
        addForeignKeyConstraint(baseColumnNames: "menu_item_id", baseTableName: "menu_section_menu_item", constraintName: "FKscnvd1y321gtrseifqtv3rngf", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_item")
    }
}
