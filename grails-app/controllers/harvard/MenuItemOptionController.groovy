package harvard

import grails.validation.ValidationException
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*

class MenuItemOptionController {

    MenuItemOptionService menuItemOptionService
    MenuItemOptionGroupService menuItemOptionGroupService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
      params.max = Math.min(max ?: 10, 100)
      respond menuItemOptionService.list(params), model:[menuItemOptionCount: menuItemOptionService.count()]
    }

    def list(Integer max, Integer page) {
      render menuItemService.list(params) as JSON
    }

    def show(Long id) {
      respond menuItemOptionService.get(id)
    }

    def create() {
      respond new MenuItemOption(params)
    }

    def save(MenuItemOption mio) {
      try {
        def savedMenuItem = menuItemOptionService.save(mio)
        render savedMenuItem as JSON
      } catch (ValidationException e) {
        render mio.errors as JSON
        return
      }
    }

    def edit(Long id) {
      respond menuItemOptionService.get(id)
    }

    def update(MenuItemOption menuItemOption) {
      if (menuItemOption == null) {
        notFound()
        return
      }

      try {
        menuItemOptionService.save(menuItemOption)
      } catch (ValidationException e) {
        respond menuItemOption.errors, view:'edit'
        return
      }

      request.withFormat {
        form multipartForm {
          flash.message = message(code: 'default.updated.message', args: [message(code: 'menuItemOption.label', default: 'MenuItemOption'), menuItemOption.id])
          redirect menuItemOption
        }
        '*'{ respond menuItemOption, [status: OK] }
      }
    }

    def delete(Long id) {
      if (id == null) {
        notFound()
        return
      }

      menuItemOptionService.delete(id)

      request.withFormat {
        form multipartForm {
          flash.message = message(code: 'default.deleted.message', args: [message(code: 'menuItemOption.label', default: 'MenuItemOption'), id])
          redirect action:"index", method:"GET"
        }
        '*'{ render status: NO_CONTENT }
      }
    }

    protected void notFound() {
      request.withFormat {
        form multipartForm {
          flash.message = message(code: 'default.not.found.message', args: [message(code: 'menuItemOption.label', default: 'MenuItemOption'), params.id])
          redirect action: "index", method: "GET"
        }
        '*'{ render status: NOT_FOUND }
      }
    }
}
