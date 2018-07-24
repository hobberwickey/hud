package harvard

class MenuItem {
    String name
    String localId

    static belongsTo = [Menu]
    static hasMany = [menus: Menu, menuItemOptionGroups: MenuItemOptionGroup]

    static constraints = {
      localId(blank: true, nullable: true)
    }
}
