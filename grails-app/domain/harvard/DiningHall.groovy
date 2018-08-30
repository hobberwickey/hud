package harvard

class DiningHall {
    String  name
    String  code
    Date    openingDate
    Date    closingDate
    boolean status
    boolean deleted

    static hasMany = [menus: Menu, orders: Orders]

    static mapping = {
      status defaultValue: true
      deleted defaultValue: false
    }

    static namedQueries = {
      available { date, meal ->
        le("closingDate", date)
        ge("openingDate", date)       
      }
    }

    static constraints = {
      status(defaultValue: true)
    }
}
