package harvard

import grails.validation.ValidationException
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*

class MyHudController {
    UserService userService

    static allowedMethods = []

    def index() {
      //// TEMP ////
      def user = userService.get(1)
      respond [:], model:[user: user]
    }

    def admin(MenuSection menuSection) {
      //// TEMP ////
      def user = userService.get(1)

      if (user != null && user.userType == "admin") {
        respond [:], model:[user: user]
      } else {
        notFound()
      }
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