package harvard

import grails.validation.ValidationException
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*

class UserController {

    UserService userService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max, Integer page) {
      respond userService.list(params), model:[userCount: userService.count()]
    }

    def list(Integer max, Integer page) {
        params.max = max ?: 10
        params.offset = (page ?: 0) * params.max

        render User.withCriteria() {
          params.each{ key, value -> 
            if (key == "user_name") {
              if (value.isNumber()) {
                eq("huid", value.toInteger())
              } else {
                or {
                  like("firstName", value + "%")
                  like("lastName", value + "%")
                }
              }
            }

            if (key == "user_type") {
              eq("userType", value)
            }

            ne("active", false)
          }

          if (params.containsKey("sortField") && params["sortField"] != "" && params.containsKey("sortOrder") && params["sortOrder"] != ""){
            order(params["sortField"], params["sortOrder"])
            order("id", params["sortOrder"] == "desc" ? "asc" : "desc")
          } else {
            order("huid", "asc")
          }

          // maxResults params.max
          // firstResult params.offset       
        } as JSON
        // render userService.list(params) as JSON
    }

    def show(Long id) {
        render userService.get(id) as JSON
    }

    def create() {
        respond new User(params)
    }

    def save(User user) {
      try {
        userService.save(user)
      } catch (ValidationException e) {
        render user.errors as JSON
        return
      }

      render user as JSON
    }

    def edit(Long id) {
        respond userService.get(id)
    }

    def update(User user) {
        if (user == null) {
            notFound()
            return
        }

        try {
            userService.save(user)
        } catch (ValidationException e) {
            respond user.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*'{ respond user, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        def user = userService.get(id)
            user.active = false

        try {
          userService.save(user)
        } catch (ValidationException e) {
          render user.errors as JSON
          return
        }

        render ([success: true] as JSON)
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
