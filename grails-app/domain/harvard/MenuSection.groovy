package harvard

class MenuSection {
    String name
    String ordering
    String localId
    Integer position
    
    static belongsTo = [Menu]

    static hasMany = [menuItems: MenuItem]

    static mapping = {
      sort 'position' 
    }

    static constraints = {
      ordering(blank: true, nullable: true)
      localId(blank: true, nullable: true)
    }
}
