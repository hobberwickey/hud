package harvard

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/admin"(controller: "menuItem", action: "index")
        "/admin/api/menu-items"(controller: "menuItem", action: "list")
        "/admin/api/menu-items/save"(controller: "menuItem", action: "save")
        
        "/admin/api/menus"(controller: "menu", action: "list")
        "/admin/api/menus/save"(controller: "menu", action: "save")

        "/admin/users"(controller: "user", action: "index")
        "/admin/users/${id}"(controller: "user", action: "edit")
        "/admin/users/new"(controller: "user", action: "create")
        "/admin/api/users"(controller: "user", action: "list")
        "/admin/api/users/save"(controller: "user", action: "save")

        "/admin/locations"(controller: "diningHall", action: "index")
        "/admin/locations/${id}"(controller: "diningHall", action: "edit")
        "/admin/locations/new"(controller: "diningHall", action: "create")
        "/admin/api/locations"(controller: "diningHall", action: "list")
        "/admin/api/locations"(controller: "diningHall", action: "save")

        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
