package harvard

class MenuSelection {
    Orders orders
    MenuItem menuItem

    static belongsTo = [Orders, MenuItem]

    static hasMany = [menuItemOptions: MenuItemOption]

    static constraints = {
    }
}
