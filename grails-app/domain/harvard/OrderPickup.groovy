package harvard

class OrderPickup {
    Date pickupDate
    Date pickupTime
    boolean pickedUp

    static belongsTo = [Orders]

    static constraints = {
    
    }
}
