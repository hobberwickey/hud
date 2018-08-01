package harvard

class MenuItem {
    String name
    String localId
    Integer position
    
    static belongsTo = [Menu]
    static hasMany = [menus: Menu, menuItemOptionGroups: MenuItemOptionGroup, menuSelections: MenuSelection]

    static mapping = {
      sort 'position' 
    }
    
    static constraints = {
      localId(blank: true, nullable: true)
    }
}
