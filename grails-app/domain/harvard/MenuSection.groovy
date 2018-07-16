package harvard

class MenuSection {
    String name
    String ordering
    String localId

    static transients = [localId]
    static belongsTo = [Menu]

    static constraints = {
      ordering(blank: true, nullable: true)
    }
}
