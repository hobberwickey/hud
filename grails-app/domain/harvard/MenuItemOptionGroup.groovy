package harvard

class MenuItemOptionGroup {
    String name
    String ordering
    String localId

    static transients = [localId]
    static belongsTo = [MenuItemOption]
    static hasMany = [menuItemOptions: MenuItemOption]
    
    static constraints = {
      ordering(blank: true, nullable: true)
    }
}
