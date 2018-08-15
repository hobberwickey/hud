package harvard

class Orders {

    Date createdOn
    Date updatedAt
    User user
    Menu menu
    DiningHall diningHall

    boolean canceled
    Date canceledOn

    static belongsTo = [User, Menu, DiningHall]
    static hasMany = [menuSelections: MenuSelection, orderPickups: OrderPickup]

    static constraints = {
      canceledOn(blank: true, nullable: true)
    }
}
