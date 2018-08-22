package harvard

class DiningHall {
    String  name
    Date    openingDate
    Date    closingDate
    boolean status
    boolean deleted

    static hasMany = [menus: Menu, orders: Orders]

    static constraints = {
      status(defaultValue: true)
    }
}
