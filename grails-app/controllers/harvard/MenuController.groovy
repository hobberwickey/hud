package harvard

import grails.validation.ValidationException
import grails.converters.JSON

import static org.springframework.http.HttpStatus.*

class MenuController {

    MenuService menuService
    MealService mealService
    MenuItemService menuItemService 
    MenuSectionService menuSectionService 

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
      params.max = Math.min(max ?: 10, 100)
      respond menuService.list(params), model:[menuCount: menuService.count()]
    }

    def list() {
      render menuService.list(params) as JSON
    }

    def show(Long id) {
      def menu = menuService.get(id)
          menu.meal =mealService.get(menu.mealId)
      
      render menu as JSON
    }

    def create() {
      respond new Menu(params)
    }

    def save(Menu menu) {
      try {
        def savedMenu = menuService.save(menu) //.save(flush: true, failOnError: true)

        savedMenu.menuSections.each { s -> 
          def newOrder = []
          (s.ordering ?: "").tokenize( ',' ).each { id -> 
            s.menuItems.each { mi -> 
              println s.name + " - " + id + ", " + mi["id"] + ", " + mi["localId"]

              if (mi["id"].toString() == id || mi["localId"] == id) {
                newOrder.push(mi["id"])
              }
            }
          }

          println s.name + " " + s.ordering
          s.ordering = newOrder.join(",")
          println s.name + " " + s.ordering

          try {
            menuSectionService.save(s)
          } catch (ValidationException e) {
            render s.errors as JSON
            return
          }
        }

        render menuService.get(savedMenu.id) as JSON
      } catch (ValidationException e) {
        render menu.errors as JSON
        return
      }

      // return menu as JSON
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
