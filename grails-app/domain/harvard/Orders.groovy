package harvard

class Orders {

    Date createdOn
    Date updatedAt
    User user
    Menu menu
    DiningHall diningHall

    boolean canceled
    Date canceledOn

    static belongsTo = [User, Menu, DiningHall, OrderPickup]
    static hasMany = [menuSelections: MenuSelection, orderPickups: OrderPickup]

    static namedQueries = {
      history { user, params -> 
        eq("user", user)
        order("updatedAt", "desc")
      }
    }

    static mapping = {
        menuSelections(cascade: "all-delete-orphan")
        orderPickups(cascade: "all-delete-orphan")
    }

    static constraints = {
      canceledOn(blank: true, nullable: true)
    }
}
