package harvard

class MenuItem {
    String name
    
    static belongsTo = [Menu]
    static hasMany = [menus: Menu, menuItemOptions: MenuItemOption]
    
    static mapping = {
      menu joinTable: [name: "mm_menu_menu_items", key: "mm_menu_item_id"]
    }

    static constraints = {
    
    }
}
