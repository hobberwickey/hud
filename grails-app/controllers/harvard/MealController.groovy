package harvard

import grails.validation.ValidationException
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*

class MealController {

    MealService mealService

    def list() {
      render mealService.list(params) as JSON
    }
}
