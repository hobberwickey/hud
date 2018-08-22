package harvard

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }   

        "/myhuds"(controller: "myHud", action: "home")
        "/myhuds/orders/${mealType}/create"(controller: "orders", action: "create")
        "/myhuds/orders/${mealType}/create/${orderId}"(controller: "orders", action: "edit")
        "/myhuds/orders/${mealType}/save"(controller: "orders", action: "save")
        "/myhuds/orders/${mealType}/save/${orderId}"(controller: "orders", action: "save")
        "/myhuds/orders/${id}"(controller: "orders", action: "show")
        "/myhuds/orders/history"(controller: "orders", action: "history")
        "/myhuds/orders/${id}/cancel"(controller: "orders", action: "cancel")

        "/admin"(controller: "myHud", action: "admin")
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
        "/admin/api/users/${id}/delete"(controller: "user", action: "delete")
        "/admin/api/users/save"(controller: "user", action: "save")
        
        "/admin/locations"(controller: "diningHall", action: "index")
        "/admin/locations/${id}"(controller: "diningHall", action: "edit")
        "/admin/locations/new"(controller: "diningHall", action: "create")
        "/admin/api/locations/${id}/delete"(controller: "diningHall", action: "delete")
        "/admin/api/locations"(controller: "diningHall", action: "list")
        "/admin/api/locations/${id}"(controller: "diningHall", action: "show")
        "/admin/api/locations/save"(controller: "diningHall", action: "save")

        "/admin/orders"(controller: "orders", action: "index")
        "/admin/orders/${id}"(controller: "orders", action: "show")
        "/admin/orders/reports"(controller: "orders", action: "reports")
        "/admin/api/orders"(controller: "orders", action: "list")
        "/admin/api/orders/search"(controller: "orders", action: "search")
        "/admin/api/orders/report"(controller: "orders", action: "report")
        "/admin/api/pickup/${id}/report"(controller: "orders", action: "mark_not_picked_up")
        
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
