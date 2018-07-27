package harvard

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }   

        "/myhuds/orders/${mealType}"(controller: "orders", action: "create")
        "/myhuds/orders/${mealType}/save"(controller: "orders", action: "saveLunch")

        "/admin"(controller: "menuItem", action: "index")

        "/admin/api/meals"(controller: "meal", action: "list")
        "/admin/menus"(controller: "menu", action: "index")
        "/admin/menus/new"(controller: "menu", action: "create")
        "/admin/menus/${id}"(controller: "menu", action: "edit")
        "/admin/api/menus"(controller: "menu", action: "list")
        "/admin/api/menus/${id}"(controller: "menu", action: "show")
        "/admin/api/menus/save"(controller: "menu", action: "save")
        "/admin/api/menu-sections"(controller: "menuSection", action: "list")
        "/admin/api/menu-sections/save"(controller: "menuSection", action: "save")

        "/admin/api/menu-items"(controller: "menuItem", action: "list")
        "/admin/api/menu-items/save"(controller: "menuItem", action: "save")
        
        "/admin/menu-item-options"(controller: "menuItemOption", action: "index")
        "/admin/api/menu-item-options"(controller: "menuItemOptionGroup", action: "list")
        "/admin/api/menu-item-options/save"(controller: "menuItemOption", action: "save")
        "/admin/api/menu-item-options/delete"(controller: "menuItemOption", action: "delete")
        "/admin/api/menu-item-options/ordering"(controller: "menuItemOptionGroup", action: "save")

        "/admin/users"(controller: "user", action: "index")
        "/admin/users/${id}"(controller: "user", action: "edit")
        "/admin/users/new"(controller: "user", action: "create")
        "/admin/api/users"(controller: "user", action: "list")
        "/admin/api/users/${id}"(controller: "user", action: "show")
        "/admin/api/users/save"(controller: "user", action: "save")
        
        "/admin/locations"(controller: "diningHall", action: "index")
        "/admin/locations/${id}"(controller: "diningHall", action: "edit")
        "/admin/locations/new"(controller: "diningHall", action: "create")
        "/admin/api/locations"(controller: "diningHall", action: "list")
        "/admin/api/locations/${id}"(controller: "diningHall", action: "show")
        "/admin/api/locations/save"(controller: "diningHall", action: "save")

        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
