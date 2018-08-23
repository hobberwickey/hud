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
        if (params.containsKey("sortField") && params["sortField"] != "" && params.containsKey("sortOrder") && params["sortOrder"] != ""){
          order(params["sortField"], params["sortOrder"])
          order("id", params["sortOrder"] == "desc" ? "asc" : "desc")
        } else {
          order("updatedAt", "desc")
        }

        eq("user", user)
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
