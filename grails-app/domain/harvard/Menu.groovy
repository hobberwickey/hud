package harvard

class Menu {
    String name
    String ordering

    static belongsTo = [Meal, MenuItem]
    static hasMany = [meals: Meal, menuItems: MenuItem]

    static mapping = {
      menus joinTable: [name: "mm_menu_meal", key: "mm_meal_id"]
      mealItems joinTable: [name: "mm_menu_menu_item", key: "mm_meal_id"]
    }
    
    static constraints = {
      ordering(blank: true, nullable: true)
    }
}
