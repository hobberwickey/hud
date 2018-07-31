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

    def availableDates() {
      def dates = [] 
      def now = new Date()
          now.set(hourOfDay: 7, minute: 0, second: 0)
      
      for (def i=0; i<7; i++){            
        def date
        use(TimeCategory) { date = now + (1 + i).days }

        dates.push(date)
      }

      return dates
    }

    def availableTimes() {
      def times = []

      def now = new Date()
          now.set(hourOfDay: 7, minute: 0, second: 0)

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

      return times
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
      }
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

    def getMain(section) {
      def main
      def options = []
      def mains = this.getMenuItem("section." + section.id + ".menuItems")
      
      if (mains[0] == null) {
        return null
      } else {
        main = mains[0]
      }
    
      if (main != null && main.menuItemOptionGroups.size() > 0){
        main.menuItemOptionGroups.each{ group -> 
          def option = this.getOptions("group." + group.id + ".menuItem." + main.id)

          if (option.size() > 0) {
            options.push(option[0])
          }
        }
      }

      return this.createMenuSelection(main, options)
    }

    def getBeverage(section) {
      def main
      def options
      def mains = this.getMenuItem("section." + section.id + ".menuItems")
      
      if (mains[0] == null) {
        return null
      } else {
        main = mains[0]
      }
    
      if (main != null && main.menuItemOptionGroups.size() > 0){
        options = this.getOptions("group." + group.id + ".menuItem." + main.id)
      }

      return this.createMenuSelection(main, options)
    }

    def getSnack(section, snackIndex) {
      def main
      def options
      def mains = this.getMenuItem("snack." + snackIndex + ".section." + section.id + ".menuItems")
      
      if (mains[0] == null) {
        return null
      } else {
        main = mains[0]
      }
    
      if (main != null && main.menuItemOptionGroups.size() > 0){
        options = this.getOptions("group." + group.id + ".menuItem." + main.id)
      }

      return this.createMenuSelection(main, options)
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

    def list(Integer max, Integer page) {
        params.max = max ?: 10
        params.offset = (page ?: 0) * params.max

        render ordersService.list(params) as JSON
    }

    def search(Integer max, Integer page) {
        params.max = max ?: 10
        params.offset = (page ?: 0) * params.max

        render ordersService.list(params) as JSON
    }

    def show(Long id) {
        render ordersService.get(id) as JSON
    }

    def create(String mealType) {
        
      def order = new Orders([:])
      def selectedMeal = Meal.findByName(mealType.capitalize())
      
      //// Pickup date / time ////
      def orderPickup = new OrderPickup()
      if (params.containsKey("pickupDate")) {
          orderPickup.pickupDate = new Date(params.pickupDate)
      }

      if (params.containsKey("pickupTime")) {
          orderPickup.pickupTime = new Date(params.pickupTime)
      } 


      //// Dining Halls ////
      if (orderPickup.pickupDate != null && orderPickup.pickupTime != null) {
        order.addToOrderPickups(orderPickup)

        if (params.containsKey("diningHallId")) {
          order.diningHall = DiningHall.withCriteria() {
            eq("id", params.diningHallId.toLong())
            lt("openingDate", orderPickup.pickupDate)
            gt("closingDate", orderPickup.pickupDate)
          }[0]

          println order.diningHall.id
          println "Dining Hall"
        }
      } 

      def openDiningHalls
      def allDiningHalls = diningHallService.list()
      if (params.pickupDate) {
          openDiningHalls = DiningHall.findAll{ openingDate <= orderPickup.pickupDate && closingDate >= orderPickup.pickupDate }
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

        if (order.menu != null) {
          ["breakfast", "sandwiches-salads", "beverages", "snacks"].each{ name -> 
            
          }

          order.menu.menuSections.each{ section -> 
            if (section.ordering != "" && section.ordering != null) {
              //TODO: not working
              def ordering = section.ordering.split(",").collect{ it.toInteger() }
              section.menuItems.sort{ a, b -> ordering.indexOf(a.id) <=> ordering.indexOf(b.id) }
            }
          }
        }
      }

      respond order, model:[
        order: order,
        allLocations: allDiningHalls, 
        availableLocations: openDiningHalls,
        orderPickup: orderPickup,

        helpers: new Helpers(params)
      ]
    }

    def save(String id) {
      def order = new Orders([:])
      def errors = [:]
      def helper = new OrderHelper(params)

      def selectedMeal = Meal.findByName(id.capitalize())

      //TEMP
      def user = User.list().first()
      order.user = user

      def orderPickup = new OrderPickup()
      if (params.containsKey("pickupDate")) {
        orderPickup.pickupDate = new Date(params.pickupDate)
      }

      if (params.containsKey("pickupTime")) {
        orderPickup.pickupTime = new Date(params.pickupTime)
      } 

      if (orderPickup.pickupDate != null && orderPickup.pickupTime != null) {
        order.addToOrderPickups(orderPickup)

        if (params.containsKey("diningHallId")) {
          order.diningHall = DiningHall.withCriteria() {
            eq("id", params.diningHallId.toLong())
            lt("openingDate", orderPickup.pickupDate)
            gt("closingDate", orderPickup.pickupDate)
          }[0]

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

        order.menu.menuSections.each{ section -> 
          switch (section.name) {
            case "breakfast":
              def items = helper.getMain(section)
              if (items != null) {
                for (def i=0; i<items.size(); i++){
                  order.addToMenuSelections(items[i])
                }
              }

              break
            case "sandwiches-salads":
              def main = helper.getMain(section)
              if (main != null) {
                order.addToMenuSelections(main)
              }

              break
            case "beverages":
              def bev = helper.getBeverage(section)
              if (bev != null) {
                order.addToMenuSelections(bev)
              }

              break
            case "snacks":
              for (def i=1; i<4; i++){
                def snack = helper.getSnack(section, i)
                if (snack != null) {
                  order.addToMenuSelections(snack)
                }
              }

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

      // render order as JSON
      redirect(controller: "orders", action: "index")
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
