package harvard

class Orders {

    Date createdOn
    Date updatedAt
    User user
    Menu menu
    DiningHall diningHall

    static belongsTo = [User, Menu, DiningHall]
    static hasMany = [menuSelections: MenuSelection, orderPickups: OrderPickup]

    static constraints = {
      
    }
}
