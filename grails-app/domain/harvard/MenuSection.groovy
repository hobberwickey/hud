package harvard

class MenuSection {
    String name
    String ordering
    String localId

    static belongsTo = [Menu]

    static hasMany = [menuItems: MenuItem]

    static mapping = {
      ordering sqlType: 'text' 
    }

    static constraints = {
      ordering(blank: true, nullable: true)
      localId(blank: true, nullable: true)
    }
}
