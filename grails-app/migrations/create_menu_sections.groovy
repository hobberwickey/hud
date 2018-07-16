databaseChangeLog = {

    changeSet(author: "joel (generated)", id: "1531495294762-1") {
        createTable(tableName: "menu_menu_section") {
            column(name: "menu_menu_sections_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "menu_section_id", type: "BIGINT")
        }
    }

    changeSet(author: "joel (generated)", id: "1531495294762-2") {
        createTable(tableName: "menu_section") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "menu_sectionPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "ordering", type: "VARCHAR(255)")

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "joel (generated)", id: "1531495294762-3") {
        addForeignKeyConstraint(baseColumnNames: "menu_section_id", baseTableName: "menu_menu_section", constraintName: "FK1v6woikka5yv1dcxk42aygktg", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu_section")
    }

    changeSet(author: "joel (generated)", id: "1531495294762-4") {
        addForeignKeyConstraint(baseColumnNames: "menu_menu_sections_id", baseTableName: "menu_menu_section", constraintName: "FKthjqjfuse8cvselrbp4im6kt", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "menu")
    }

    changeSet(author: "joel (generated)", id: "1531495294762-5") {
        dropColumn(columnName: "ordering", tableName: "menu")
    }
}
