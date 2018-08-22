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
      render DiningHall.withCriteria {
        ne("deleted", true)
      } as JSON
    }


    def show(Long id) {
      render diningHallService.get(id) as JSON
    }

    def create() {
      respond new DiningHall(params)
    }

    def save(DiningHall diningHall) {
      if (diningHall == null) {
          notFound()
          return
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

        def location = diningHallService.get(id)
            location.deleted = true

        try {
          diningHallService.save(location)
        } catch (ValidationException e) {
          render diningHall.errors as JSON
          return
        }

        render ([success: true] as JSON)
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
