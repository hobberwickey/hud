package harvard

class Meal {
    String name 

    static belongsTo = [DiningHall]
    static hasMany = [menus: Menu]

    static mapping = {
      menus joinTable: [name: "mm_meal_menus", key: "mm_meal_id"]
    }

    static constraints = {
    
    }
}
