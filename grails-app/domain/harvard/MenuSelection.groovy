package harvard

class MenuSelection {

    static belongsTo = [Orders, MenuItem]

    static hasMany = [MenuItemOption]

    static constraints = {
    }
}
