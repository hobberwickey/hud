package harvard

class DiningHall {
    String  name
    Date    openingDate
    Date    closingDate
    boolean status

    static hasMany = [menus: Menu]

    static constraints = {
      active(defaultValue: true)
    }
}
