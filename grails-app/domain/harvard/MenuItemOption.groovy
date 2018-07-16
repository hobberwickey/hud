package harvard

class MenuItemOption {
    String name
    String localId

    static transients = [localId]
    static belongsTo = [MenuItemOptionGroup]
    static hasMany = [menuItemOptionGroups: MenuItemOptionGroup]
    
    static constraints = {
    
    }
}
