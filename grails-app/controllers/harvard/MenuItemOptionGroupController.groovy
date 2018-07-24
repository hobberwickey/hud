package harvard

import grails.validation.ValidationException
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*


class MenuItemOptionGroupController {

    MenuItemOptionGroupService menuItemOptionGroupService
    MenuItemOptionService menuItemOptionService
    // ObjectMergerService objMerger = new ObjectMergerService()

    static allowedMethods = [save: "POST"]
    
    def list(Integer max, Integer page) {
      render menuItemOptionGroupService.list(params) as JSON
    }

    def save(MenuItemOptionGroup group) {
      try {
        def savedOptions = menuItemOptionGroupService.save(group)
        render savedOptions as JSON
      } catch (ValidationException e) {
        render group.errors as JSON
        return
      } 

      render group as JSON     
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
