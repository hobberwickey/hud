package harvard

class MenuSelection {
    Orders orders
    MenuItem menuItem
    Integer snackIndex
    Integer orderIndex

    static belongsTo = [Orders, MenuItem]

    static hasMany = [menuItemOptions: MenuItemOption]

    static constraints = {
    
    }
}
