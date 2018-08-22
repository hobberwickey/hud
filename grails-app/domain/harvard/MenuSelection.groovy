package harvard

class MenuSelection {
    Orders orders
    MenuItem menuItem
    Integer snackIndex

    static belongsTo = [Orders, MenuItem]

    static hasMany = [menuItemOptions: MenuItemOption]

    static constraints = {
    
    }
}
