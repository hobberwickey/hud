package harvard

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import groovy.time.*

class Helpers {
    Map params

    Helpers(params) {
        this.params = params
    }

    def replaceParam(key, value) {
        def newParams = [:]
        def ignoredKeys = ["controller", "action"]

        this.params.each{ k, v ->
           if (ignoredKeys.contains(k)){
                return
            }

            if (k == key) {
                newParams[k] = value
            } else {
                newParams[k] = v
            }
        }

        if (!newParams.containsKey(key)){
            newParams[key] = value
        }

        return this.toQueryString(newParams)
    }

    def toQueryString(params) {
        def parts = []

        params.each{ k, v -> 
            parts.push(k + "=" + v)
        }

        return "?" + parts.join("&")
    }

    def isSameDate(date1, date2) {
        if (date1 == null || date2 == null) return false

        return date1.format("yyyy-MM-dd HH:mm:ss") == date2.format("yyyy-MM-dd HH:mm:ss")
    }
}

class OrderValidator {
  def validateBreakfast(order) {

  } 

  def validateLunch(order) {

  }

  def validateDinner(order){

  }
}

class OrderHelper {
    Map params

    OrderHelper(params) {
        this.params = params
    }

    def getMenuItem(key) {
      return this.params.list(key).collect{ id ->
        return MenuItem.get(id.toInteger())
      }
    }

    def getOptions(key) {
      return this.params.list(key).collect{ id -> 
        return MenuItemOption.get(id.toInteger())
      }.removeAll { !it }
    }

    def createMenuSelection(item, options) {
      if (item != null) {
        return new MenuSelection(menuItem: item, menuItemOptions: options)
      } else {
        return null
      }
    }

    def getBreakfastItems(group) {

    }

    def getMain(group) {
      def main
      def options
      def selection
      def mains = this.getMenuItem("section." + group.id + ".menuItems")
      println mains
      println "MAINS"
      if (mains.size() >  1){
        throw new RuntimeException("Only one sandwich or salad can be selected")
      } else if (mains.size() == 0) {
        throw new RuntimeException("One sandwich or salad must be selected")
      } else {
        if (mains[0] == null) {
          throw new RuntimeException("Please select one sandwich or salad")
        } else {
          main = mains[0]
        }
      }

      if (main != null && main.menuItemOptionGroups.size() > 0){
        options = this.getOptions("group." + group.id + ".menuItem." + main.id)
      }

      return this.createMenuSelection(main, options)
    }

    def getBeverage(group) {

    }

    def getSnacks(group) {

    }
}

class OrdersController {

    OrdersService ordersService
    DiningHallService diningHallService
    MenuService menuService
    MealService mealService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ordersService.list(params), model:[orderCount: ordersService.count()]
    }

    def show(Long id) {
        respond ordersService.get(id)
    }

    def create(String mealType) {
        def dates = [] 
        def times = []

        def now = new Date()
            now.set(hourOfDay: 7, minute: 0, second: 0)
        
        for (def i=0; i<7; i++){            
            def date
            use(TimeCategory) { date = now + (1 + i).days }

            dates.push(date)
        }

        for (def i=0; i<21; i++){
            def time
            def hour = (int)(i / 2)

            if (i % 2 == 0) {
                use(TimeCategory) { time = now + hour.hour }
            } else {
                use(TimeCategory) { time = now + hour.hour + 30.minutes }
            }

            times.push(time)
        }

        def selectedDate
        def selectedTime
        def selectedDiningHall
        def selectedMenu
        def selectedMeal = Meal.findByName("Lunch")
        
        //// Meal ////
        
        //// Pickup date / time ////
        if (params.containsKey("pickupDate")) {
            selectedDate = new Date(params.pickupDate)
        }

        if (params.containsKey("pickupTime")) {
            selectedTime = new Date(params.pickupTime)
        }

        //// Dining Halls ////
        def openDiningHalls
        def allDiningHalls = diningHallService.list()
        if (params.pickupDate) {
            openDiningHalls = DiningHall.findAll{ openingDate <= selectedDate && closingDate >= selectedDate }
        }

        if (params.containsKey("diningHallId")) {
            selectedDiningHall = diningHallService.get(params.diningHallId)
        }

        if (selectedDiningHall != null) {
            selectedMenu = Menu.withCriteria() {
                diningHalls {
                    idEq(selectedDiningHall.id)
                }

                meal {
                    idEq(selectedMeal.id)
                }
            }[0]

            selectedMenu.menuSections.each{ section -> 
                if (section.ordering != "" && section.ordering != null) {
                    def ordering = section.ordering.split(",").collect{ it.toInteger() }
                    section.menuItems.sort{ a, b -> ordering.indexOf(a.id) <=> ordering.indexOf(b.id) }
                }
            }
        }

        respond new Orders(params), model:[
            dates: dates, 
            times: times, 
            locations: allDiningHalls, 
            availableLocations: openDiningHalls,

            selectedMeal: selectedMeal,
            selectedDate: selectedDate,
            selectedTime: selectedTime,
            selectedLocation: selectedDiningHall,
            selectedMenu: selectedMenu, 

            helpers: new Helpers(params)
        ]
    }

    def save(String mealType) {
      def order = new Orders([:])
      def errors = [:]
      def helper = new OrderHelper(params)

      def selectedMeal = Meal.findByName(mealType.capitalize())

      //TEMP
      def user = User.list().first()
      order.user = user

      println params
      println "Params"
      //// Pickup date / time ////
      def orderPickup = new OrderPickup()
      if (params.containsKey("pickupDate")) {
          orderPickup.pickupDate = new Date(params.pickupDate)
      } else {
          errors["pickup_date"] = "You must selected a pick up date"
      }

      if (params.containsKey("pickupTime")) {
          orderPickup.pickupTime = new Date(params.pickupTime)
      } else {
          errors["pickup_time"] = "You must select a pick up time"
      }

      println orderPickup
      println "Order Pickup"
      if (orderPickup.pickupDate != null && orderPickup.pickupTime != null) {
        order.addToOrderPickups(orderPickup)

        if (params.containsKey("diningHallId")) {
          order.diningHall = DiningHall.withCriteria() {
            // and {
              eq("id", params.diningHallId.toLong())
              lt("openingDate", orderPickup.pickupDate)
              gt("closingDate", orderPickup.pickupDate)
            // }
          }[0]

          println params.diningHallId.toLong()
          println orderPickup.pickupDate
          println order.diningHall
          println "Dining Hall"
        } else {
          errors["dining_hall"] = "You must select a pick up location"
        }
      } 

      if (order.diningHall != null) { 
        order.menu = Menu.withCriteria() {
          diningHalls {
            idEq(order.diningHall.id)
          }

          meal {
            idEq(selectedMeal.id)
          }
        }[0]

        println order.menu
        println "Menu"
        order.menu.menuSections.each{ section -> 
          switch (section.name) {
            case "sandwiches-salads":
              def main = helper.getMain(section)
              println main
              order.addToMenuSelections(main)

              break
            case "beverages":
      

              break
            case "snacks":
              

              break
            default: 
              break
          }
        }
        
        order.createdOn = new Date()
        order.updatedAt = new Date()

        try {
          ordersService.save(order)
        } catch (ValidationException e) {
          println e
          render errors as JSON
          return
        }
      }

      render order as JSON
    }

    def edit(Long id) {
        respond ordersService.get(id)
    }

    def update(Orders order) {
        if (order == null) {
            notFound()
            return
        }

        try {
            ordersService.save(order)
        } catch (ValidationException e) {
            respond order.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'order.label', default: 'Order'), order.id])
                redirect order
            }
            '*'{ respond order, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        ordersService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'order.label', default: 'Order'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'order.label', default: 'Order'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
