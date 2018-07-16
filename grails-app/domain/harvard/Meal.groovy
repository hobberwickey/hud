package harvard

class Meal {
    String name 

    static belongsTo = [DiningHall]
    static hasMany = [menus: Menu]

    static constraints = {
    
    }
}
