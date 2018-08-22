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

    static constraints = {
    
    }
}
