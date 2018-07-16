package harvard

class Menu {
    String name
    String localId

    static transients = [localId]

    static belongsTo = [Meal, MenuItem]
    static hasMany = [menuItems: MenuItem, menuSections: MenuSection]
    
    static constraints = {
      
    }
}
