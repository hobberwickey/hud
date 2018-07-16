package harvard

class MenuItem {
    String name
    String localId

    static transients = [localId]
    static belongsTo = [Menu]
    static hasMany = [menus: Menu, menuItemOptionGroups: MenuItemOptionGroup]

    static constraints = {
    
    }
}
