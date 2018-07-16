package harvard

import grails.validation.ValidationException
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*


class MenuItemOptionGroupController {

    MenuItemOptionGroupService menuItemOptionGroupService
    MenuItemOptionService menuItemOptionService

    static allowedMethods = [save: "POST"]
    
    def list(Integer max, Integer page) {
      render menuItemOptionGroupService.list(params) as JSON
    }

    def save() {
      def json = request.JSON
      def group = menuItemOptionGroupService.get(json.get("id"))

      if (group == null) {
        group = new MenuItemOptionGroup(json)
      } else {
        def keys = json.keys()

        while (keys.hasNext()) {
          def key = keys.next()

          if (key == "id" || key == "menuItemOptions") {
            continue
          } else {
            group[key] = json.get(key)
          }
        }
      }

      json.get("menuItemOptions").each { o ->
        def item = menuItemOptionService.get(o["id"])
        
        if (group != null) {
          if (o["deleted"] != true) {
            group.addToMenuItemOptions(item)
          } else {
            group.removeFromMenuItemOptions(item)
          }
        }
      }

      try {
        menuItemOptionGroupService.save(group)
      } catch (ValidationException e) {
        render group.errors as JSON
        return
      }

      render json as JSON
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'menuItemOptionGroup.label', default: 'MenuItemOptionGroup'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
