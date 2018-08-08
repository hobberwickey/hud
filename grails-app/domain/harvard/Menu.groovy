package harvard

class Menu {
    String  name
    String  localId
    Meal    meal
    boolean deleted

    static belongsTo = [Meal,DiningHall]
    static hasMany = [menuSections: MenuSection, orders: Orders, diningHalls : DiningHall]
    
    static constraints = {
      localId(blank: true, nullable: true)
    }

    def afterSave() {
      println this
    }
}
