package harvard

class MenuItemOption {
    String name
    String localId
    Integer position
    
    static belongsTo = [MenuItemOptionGroup]
    static hasMany = [menuItemOptionGroups: MenuItemOptionGroup, menuSelections: MenuSelection]

    static constraints = {
      localId(blank: true, nullable: true)
    }
}
