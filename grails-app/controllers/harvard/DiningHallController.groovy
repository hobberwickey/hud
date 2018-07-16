package harvard

import grails.validation.ValidationException
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*

class DiningHallController {

    DiningHallService diningHallService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
      respond diningHallService.list(params), model:[diningHallCount: diningHallService.count()]
    }

    def list(Integer max, Integer page) {
      render diningHallService.list(params) as JSON
    }


    def show(Long id) {
      respond diningHallService.get(id)
    }

    def create() {
      respond new DiningHall(params)
    }

    def save() {
      def json = request.JSON
      def diningHall = diningHallService.get(json.get("id"))

      if (diningHall == null) {
        diningHall = new DiningHall(json)
      } else {
        def keys = json.keys()

        while (keys.hasNext()) {
          def key = keys.next()

          if (key == "id") {
            continue
          } else {
            diningHall[key] = json.get(key)
          }
        }
      }


      try {
        diningHallService.save(diningHall)
      } catch (ValidationException e) {
        render diningHall.errors as JSON
        return
      }

      render diningHall as JSON
    }

    def edit(Long id) {
        respond diningHallService.get(id)
    }

    def update(DiningHall diningHall) {
        if (diningHall == null) {
            notFound()
            return
        }

        try {
            diningHallService.save(diningHall)
        } catch (ValidationException e) {
            respond diningHall.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'diningHall.label', default: 'DiningHall'), diningHall.id])
                redirect diningHall
            }
            '*'{ respond diningHall, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        diningHallService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'diningHall.label', default: 'DiningHall'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'diningHall.label', default: 'DiningHall'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
