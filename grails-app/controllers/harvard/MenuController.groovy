package harvard

import grails.validation.ValidationException
import grails.converters.JSON

import static org.springframework.http.HttpStatus.*

class MenuController {

    MenuService menuService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
      params.max = Math.min(max ?: 10, 100)
      respond menuService.list(params), model:[menuCount: menuService.count()]
    }

    def list() {
      render menuService.list(params) as JSON
    }

    def show(Long id) {
      respond menuService.get(id)
    }

    def create() {
      respond new Menu(params)
    }

    def save() {
      def json = request.JSON
      def menu = menuService.get(json.get("id"))

      if (menu == null) {
        menu = new Menu(json)
      } else {
        def keys = json.keys()

        while (keys.hasNext()) {
          def key = keys.next()

          if (key == "id") {
            continue
          }  else {
            menu[key] = json.get(key)
          }
        }
      }

      try {
        menuService.save(menu)
      } catch (ValidationException e) {
        render menu.errors as JSON
        return
      }

      render menu as JSON
    }

    def edit(Long id) {
        respond menuService.get(id)
    }

    def update(Menu menu) {
        if (menu == null) {
            notFound()
            return
        }

        try {
            menuService.save(menu)
        } catch (ValidationException e) {
            respond menu.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'menu.label', default: 'Menu'), menu.id])
                redirect menu
            }
            '*'{ respond menu, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        menuService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'menu.label', default: 'Menu'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
