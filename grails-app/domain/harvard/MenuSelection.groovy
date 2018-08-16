package harvard

class MenuSelection {
    Orders orders
    MenuItem menuItem

    static belongsTo = [Orders, MenuItem, MenuItemOption]

    static hasMany = [menuItemOptions: MenuItemOption]

    static constraints = {
    
    }
}
