package harvard

class User {
  Integer huid
  String  userType
  String  firstName
  String  lastName
  String  email
  boolean active
  boolean blocked

  static hasMany = [diningHalls: DiningHall, orders: Orders]
    

  static constraints = {
    active(defaultValue: true)
    blocked(defaultValue: false)
  }
}

// Search on harvard_id, first name, last name - partial search on beginning of names
