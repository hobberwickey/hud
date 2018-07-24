package harvard

class Menu {
    String name
    String localId
    Meal meal

    static belongsTo = [meal:Meal]
    static hasMany = [menuSections: MenuSection]
    
    static constraints = {
      localId(blank: true, nullable: true)
    }

    def afterSave() {
      println this
    }
}
