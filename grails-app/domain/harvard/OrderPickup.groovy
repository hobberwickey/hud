package harvard

class OrderPickup {
    Date pickupDate
    Date pickupTime
    boolean pickedUp
    Orders orders

    static belongsTo = [Orders]

    static mapping = {
      pickedUp defaultValue: true
      sort 'pickupDate' 
    }

    static namedQueries = {
      search { params -> 
        params.each{ key, value -> 
          if (key == "user") {
            if (value.isNumber()) {
              orders {
                user {
                  eq("huid", value.toInteger())
                }
              }
            } else {
              orders {
                user {
                  or {
                    like("firstName", value + "%")
                    like("lastName", value + "%")
                  }
                }
              }
            }
          }

          if (key == "location") {
            orders {
              diningHall {
                eq("name", value)
              }
            }
          }

          if (key == "meal") {
            orders {
              menu {
                meal {
                  eq("name", value)
                }
              }
            }
          }

          if (key == "start_date") {
            gt("pickupDate", new Date().parse("yyyy-MM-dd", value))
          }

          if (key == "end_date"){
            lt("pickupDate", new Date().parse("yyyy-MM-dd", value))
          }

          if (key == "status") {
            eq("pickedUp", value.toBoolean())
          }
        }       
        
        if (params.containsKey("sortField") && params["sortField"] != "" && params.containsKey("sortOrder") && params["sortOrder"] != ""){
          order(params["sortField"], params["sortOrder"])
          order("id", params["sortOrder"] == "desc" ? "asc" : "desc")
        } else {
          order("pickupDate", "desc")
        }
      }
    }

    static constraints = {
    
    }
}
