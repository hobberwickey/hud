package harvard

import grails.validation.ValidationException
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*

class MenuItemController {

    MenuItemService       menuItemService
    MenuItemOptionService menuItemOptionService
    MenuService           menuService

    static allowedMethods = [create: "POST", save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        respond([])
    }

    def list(Integer max, Integer page) {
        // params.max = Math.min(max ?: 10, 100)
        // params.page = page ?: 0

        render menuItemService.list(params) as JSON
    }

    def show(Long id) {
        respond menuItemService.get(id)
    }

    def create() {
        respond([])
    }

    def save() {
        def json = request.JSON
        def menuItem = menuItemService.get(json.get("id"))

        if (menuItem == null) {
            menuItem = new MenuItem(json)
        } else {
            def keys = json.keys()

            while (keys.hasNext()) {
                def key = keys.next()

                if (key == "id") {
                    continue
                } else if (key == "menus") {
                    println json[key]
                    
                    def menus = json[key]
                    menus.each { m ->
                        def menu = menuService.get(m["id"])

                        if (menu != null) {
                            if (m["deleted"] != true) {
                                menuItem.addToMenus(menu)
                            } else {
                                menuItem.removeFromMenus(menu)
                            }
                        }
                    }
                } else {
                    menuItem[key] = json.get(key)
                }
            }
        }

        try {
            menuItemService.save(menuItem)
        } catch (ValidationException e) {
            render menuItem.errors as JSON
            return
        }

        render menuItem as JSON
    }

    def edit(Long id) {
        respond menuItemService.get(id)
    }

    def update(MenuItem menuItem) {
        if (menuItem == null) {
            notFound()
            return
        }

        try {
            menuItemService.save(menuItem)
        } catch (ValidationException e) {
            respond menuItem.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'menuItem.label', default: 'MenuItem'), menuItem.id])
                redirect menuItem
            }
            '*'{ respond menuItem, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        menuItemService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'menuItem.label', default: 'MenuItem'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'menuItem.label', default: 'MenuItem'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
