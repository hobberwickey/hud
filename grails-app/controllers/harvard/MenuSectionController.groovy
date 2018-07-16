package harvard

import grails.validation.ValidationException
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*

class MenuSectionController {

    MenuSectionService menuSectionService
    MenuService menuService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def list(Integer max, Integer page) {
      render menuSectionService.list(params) as JSON
    }

    def save(MenuSection menuSection) {
      def json = request.JSON
      def section = menuSectionService.get(json.get("id"))

      if (section == null) {
        section = new MenuSection(json)
      } else {
        def keys = json.keys()

        while (keys.hasNext()) {
          def key = keys.next()

          if (key == "id" || key == "menu") {
              continue
          } else {
              section[key] = json.get(key)
          }
        }
      }

      def m = json.get("menu")
      if (m != null) { 
        def menu = menuService.get(m["id"])
        
        if (menu != null) {
          menu.addToMenuSections(section)
        }
        
      }

      try {
        menuSectionService.save(section)
      } catch (ValidationException e) {
        render section.errors as JSON
        return
      }

      render section as JSON
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'menuSection.label', default: 'MenuSection'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
