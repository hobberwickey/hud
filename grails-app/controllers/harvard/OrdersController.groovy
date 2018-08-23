package harvard

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import groovy.time.*

class OrdersController {

    OrdersService ordersService
    DiningHallService diningHallService
    MenuService menuService
    MenuSelectionService menuSelectionService
    MealService mealService
    UserService userService 

    def sessionFactory

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ordersService.list(params), model:[diningHalls: diningHallService.list(), meals: mealService.list()]
    }

    def reports(Integer max) {
      params.max = Math.min(max ?: 10, 100)
      respond ordersService.list(params), model:[diningHalls: diningHallService.list(), meals: mealService.list()]
    } 

    def history(Integer max, Integer page) {
      //Get User
      params.max = max ?: 20
      params.offset = (page ?: 0) * params.max

      def user = userService.get(1)
      // def orders = Orders.withCriteria{
      //   eq("user", user)
      //   order("updatedAt", "desc")
      //   maxResults(params.max)
      //   firstResult(params.offset)
      // }

      if (request.xhr) {
        def output = [:]
        def count = Orders.history(user, params).count()
        def results = Orders.history(user, params).list([max: params.max, offset: params.offset])

        output.count = count
        output.results = results

        response.setHeader('Content-Type', 'application/json')
        render output as JSON
      } else {
        respond ordersService.list(params), [:]
      }

      // render OrderPickup.get(122) as JSON
    }

    def list(Integer max, Integer page) {
        params.max = max ?: 20
        params.offset = (page ?: 0) * params.max

        render ordersService.list(params) as JSON
    }

    def search(Integer max, Integer page) {
      max = max ?: 20
      def offset = (page ?: 0) * max
      
      params.end_date = params.end_date ?: new Date().format("yyyy-MM-dd")
      params.start_date = params.start_date ?: (new Date() - 7).format("yyyy-MM-dd")
      
      // render OrderPickup.createCriteria().list([max: max, offset: offset]) {
        // params.each{ key, value -> 
        //   if (key == "user") {
        //     if (value.isNumber()) {
        //       orders {
        //         user {
        //           eq("huid", value.toInteger())
        //         }
        //       }
        //     } else {
        //       orders {
        //         user {
        //           or {
        //             like("firstName", value + "%")
        //             like("lastName", value + "%")
        //           }
        //         }
        //       }
        //     }
        //   }

        //   if (key == "location") {
        //     orders {
        //       diningHall {
        //         eq("name", value)
        //       }
        //     }
        //   }

        //   if (key == "meal") {
        //     orders {
        //       menu {
        //         meal {
        //           eq("name", value)
        //         }
        //       }
        //     }
        //   }

        //   if (key == "start_date") {
        //     gt("pickupDate", new Date().parse("yyyy-MM-dd", value))
        //   }

        //   if (key == "end_date"){
        //     lt("pickupDate", new Date().parse("yyyy-MM-dd", value))
        //   }

        //   if (key == "status") {
        //     eq("pickedUp", value.toBoolean())
        //   }
        // }       
      
        // order "pickupDate",  "desc"
      // } as JSON
      def output = [:]
      def count = OrderPickup.search(params).count()
      def results = OrderPickup.search(params).list([max: max, offset: offset])

      output.count = count
      output.results = results

      response.setHeader('Content-Type', 'application/json')
      render output as JSON
    }

    def show(Long id) {
        render ordersService.get(id) as JSON
    }

    def create(String mealType, Integer orderId) {
      def order = new Orders([:])
      def selectedMeal = Meal.findByName(mealType.capitalize())
      
      //// Pickup date / time ////
      def orderPickup = new OrderPickup([
        pickupDate: (new Date() + 1), 
        pickupTime: (new Date([hours: 7, minutes: 0, seconds: 0]) + 1)
      ])

      if (params.containsKey("pickupDate") && params["pickupTime"] != "") {
          orderPickup.pickupDate = new Date(params.pickupDate)
      }

      if (params.containsKey("pickupTime") && params["pickupTime"] != "") {
          orderPickup.pickupTime = new Date(params.pickupTime)
      } 


      //// Dining Halls ////
      if (orderPickup.pickupDate != null && orderPickup.pickupTime != null) {
        order.addToOrderPickups(orderPickup)

        if (params.containsKey("diningHallId") && params["diningHallId"] != "") {
          order.diningHall = DiningHall.withCriteria() {
            eq("id", params.diningHallId.toLong())
            lt("openingDate", orderPickup.pickupDate)
            gt("closingDate", orderPickup.pickupDate)
          }[0]
        }
      } 

      def openDiningHalls
      def allDiningHalls = DiningHall.withCriteria {
        ne("deleted", true)
      }

      if (orderPickup.pickupDate != null && orderPickup.pickupTime != null) {
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
          order.menu.menuSections.each{ section -> 
            if (section.ordering != "" && section.ordering != null) {
              //TODO: not working
              def ordering = section.ordering.split(",").collect{ it.toInteger() }
              section.menuItems.sort{ a, b -> ordering.indexOf(a.id) <=> ordering.indexOf(b.id) }
            }
          }
        }
      }

      def model = [
        order: order,
        allLocations: allDiningHalls, 
        availableLocations: openDiningHalls,
        orderPickup: orderPickup,
        meal: selectedMeal,
        errors: [
          "pickupDate": [],
          "pickupTime": [],
          "diningHall": [],
          "breakfast": [],
          "sandwiches-salads": [],
          "beverages": [],
          "snacks": []
        ]
      ]

      if (request.xhr) {
        render model as JSON
      } else {
        model["helpers"] = new Helpers(params)
        respond order, model: model
      }

    }

    def edit(String mealType, Integer orderId) {
      def order = ordersService.get(orderId)
      def openDiningHalls
      def allDiningHalls = DiningHall.withCriteria {
        ne("deleted", true)
      }
      def selectedMeal = Meal.findByName(mealType.capitalize())
      
      if (order.orderPickups != null && order.orderPickups.size() > 0) {
          openDiningHalls = DiningHall.findAll{ openingDate <= order.orderPickups.sort({ a, b -> a.pickupDate<=>b.pickupDate}).first().pickupDate && closingDate >= order.orderPickups[0].pickupDate }
      }

      def validator = new OrderValidator()
          validator.validate(selectedMeal, order)    
        

      def model = [
        order: order,
        allLocations: allDiningHalls, 
        availableLocations: openDiningHalls,
        orderPickup: order.orderPickups.size() > 0 ? order.orderPickups.sort({ a, b -> a.pickupDate<=>b.pickupDate}).first() : new OrderPickup(),
        meal: selectedMeal,
        errors: validator.errors,
        helpers: new Helpers(params)
      ]

      render(view: "create", model: model)
    }

    def save(String mealType, Integer orderId) {
      def order
      if (orderId != null){
        order = ordersService.get(orderId)
      } else {
        order = new Orders([:])
      }

      // if (params.containsKey("id") && params["id"].isNumber()){
      //   order = ordersService.get(params["id"]) 

      //   if (order == null){
      //     order = new Orders([:])
      //   }  
      // }

      def helper = new OrderHelper(params)
      def selectedMeal = Meal.findByName(mealType.capitalize())

      //TEMP
      def user = User.list().first()
      order.user = user

      order.orderPickups.collect().each { 
        order.removeFromOrderPickups(it)
      }
      def orderPickup = new OrderPickup(pickedUp: true)
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

          if (order.diningHall != null && params.repeated == "true" && params.repeatEndDate) {
            def endDate = new Date().parse("yyyy-MM-dd", params.repeatEndDate)
            def nextPickup = new Date(params.pickupDate).plus(7)

            while (nextPickup < endDate && nextPickup < order.diningHall.closingDate){
              def repeatPickup = new OrderPickup(pickupDate: nextPickup, pickupTime: orderPickup.pickupTime, pickedUp: true)
              order.addToOrderPickups(repeatPickup)

              nextPickup = nextPickup.plus(7)
            }
          }

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

        order.menuSelections.collect().each { selection -> 
          order.removeFromMenuSelections(selection)
        }
        order.menu.menuSections.each{ section -> 
          switch (section.name) {
            case "breakfast":
              def items = helper.getBreakfast(section)
              
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
        
        if (order.id == null) {
          order.createdOn = new Date()
        }
        order.updatedAt = new Date()

        def validator = new OrderValidator()
            validator.validate(selectedMeal, order)    
        
        // println "VALID? " + validator.valid()
        if (validator.valid()) {
          try {
            // ordersService.save(order)
            order.save(flush: true)
            order.menuSelections.each{ms -> 
              ms.save(flush: true)
            }
          } catch (ValidationException e) {
            println e
            render errors as JSON
            return
          }
          
          redirect(controller: "orders", action: "history")
        } else {
          def openDiningHalls
          def allDiningHalls = DiningHall.withCriteria {
            ne("deleted", true)
          }

          if (order.orderPickups != null && order.orderPickups.size() > 0) {
              openDiningHalls = DiningHall.findAll{ openingDate <= order.orderPickups[0].pickupDate && closingDate >= order.orderPickups[0].pickupDate }
          }

          def model = [
            order: order,
            allLocations: allDiningHalls, 
            availableLocations: openDiningHalls,
            orderPickup: order.orderPickups[0] == null ? new OrderPickup() : order.orderPickups[0],
            meal: selectedMeal,
            errors: validator.errors,
            helpers: new Helpers(params)
          ]

          render(view: "create", model: model)
        }
      }

      def validator = new OrderValidator()
          validator.validate(selectedMeal, order)

      def openDiningHalls
      def allDiningHalls = DiningHall.withCriteria {
        ne("deleted", true)
      }

      if (order.orderPickups != null && order.orderPickups.size() > 0) {
          openDiningHalls = DiningHall.findAll{ openingDate <= order.orderPickups[0].pickupDate && closingDate >= order.orderPickups[0].pickupDate }
      }

      def model = [
        order: order,
        allLocations: allDiningHalls, 
        availableLocations: openDiningHalls,
        orderPickup: order.orderPickups != null && order.orderPickups[0] != null ? order.orderPickups[0] : new OrderPickup(),
        meal: selectedMeal,
        errors: validator.errors,
        helpers: new Helpers(params)
      ]

      render(view: "create", model: model)
      // redirect(controller: "orders", action: "index")
    }

    def report() {
      def max = params.max ?: 20
      def offset = (params.page ?: 0) * max

      def countSelect = """
        SELECT 
          COUNT(t.menu_item_id)
        FROM (
      """

      def part1 = """
        SELECT 
          popularity.menu_item_id as menu_item_id, 
          popularity.menu_item_name as menu_item_name, 
          popularity.menu_item_popularity as menu_item_popularity,
          dining_hall.id as dining_hall_id,
          dining_hall.name as dining_hall_name,
          meal.name as meal
        FROM menu_selection 
        LEFT JOIN (
          SELECT 
            menu_item.id as menu_item_id, 
            menu_item.name as menu_item_name, 
            count(menu_item.id) as menu_item_popularity 
          FROM menu_selection 
            LEFT JOIN menu_item ON menu_selection.menu_item_id = menu_item.id
            LEFT JOIN orders ON menu_selection.orders_id = orders.id
            LEFT JOIN order_pickup ON order_pickup.orders_id = orders.id
            LEFT JOIN dining_hall ON orders.dining_hall_id = dining_hall.id
            LEFT JOIN menu ON orders.menu_id = menu.id
            LEFT JOIN meal ON menu.meal_id = meal.id
      """

      def part2 = """
          GROUP BY 
            menu_item.id,
            dining_hall.id, 
            dining_hall.name, 
            meal.id
        ) AS popularity ON popularity.menu_item_id = menu_selection.menu_item_id
        LEFT JOIN orders ON menu_selection.orders_id = orders.id
        LEFT JOIN order_pickup ON order_pickup.orders_id = orders.id
        LEFT JOIN dining_hall ON orders.dining_hall_id = dining_hall.id
        LEFT JOIN menu ON orders.menu_id = menu.id
        LEFT JOIN meal ON menu.meal_id = meal.id
      """

      def part3 = """
        GROUP BY 
          popularity.menu_item_id, 
          popularity.menu_item_name, 
          popularity.menu_item_popularity, 
          dining_hall.id, 
          dining_hall.name, 
          meal.name
        ORDER BY popularity.menu_item_popularity DESC
      """

      def part4 = """
        LIMIT :max
        OFFSET :offset
      """

      def whereClaus = ""
      def filterClauses = []
      def filterValues = [:]

      ["start_date", "end_date", "location", "meal"].each{ key -> 
        if (params.containsKey(key)) {
          if (key == "start_date"){ filterClauses.push('order_pickup.pickup_date > :start_date') }
          if (key == "end_date") { filterClauses.push('order_pickup.pickup_date < :end_date') }
          if (key == "location") { filterClauses.push('dining_hall.name = :location') }
          if (key == "meal") { filterClauses.push('meal.name = :meal') }

          filterValues[key] = params[key]
        }
      }
      
      if (filterClauses.size() > 0){
        whereClaus = "  WHERE " + filterClauses.join(" AND \n") + " \n"
      }

      // println part1 + whereClaus + part2 + whereClaus + part3

      def session = sessionFactory.getCurrentSession()
      def query   = session.createSQLQuery(part1 + whereClaus + part2 + whereClaus + part3 + part4)
      def countQuery = session.createSQLQuery(countSelect + part1 + whereClaus + part2 + whereClaus + part3 + ") as t")

      filterValues.each{ key, value -> 
        query.setParameterList(key, value)
        countQuery.setParameterList(key, value)
      }

      query.setInteger("max", max.toInteger())
      query.setInteger("offset", offset.toInteger())

      def output = [:]
      def count = countQuery.list()
      def results = query.list()
      
      output.count = count
      output.results = results
      
      render output as JSON
    }

    def cancel(Integer id) {
      def order = ordersService.get(id)

      // def user = getCurrentUser()
      // if (order == null || order.user.id != user.id) {
      //   notFound()
      //   return
      // }

      order.canceled = true
      order.canceledOn = new Date()

      try {
        ordersService.save(order)
      } catch (ValidationException e) {
        println e
        render e as JSON
        return
      }

      redirect(controller: "orders", action: "history") 
    }

    def mark_not_picked_up(Integer id) {
      def pickup = OrderPickup.get(id)

      if (pickup == null) {
        notFound()
        return
      } 
      
      pickup.pickedUp = false

      try {
        pickup.save(flush: true, failOnError: true)
        render pickup as JSON
      } catch (ValidationException e) {
        println e
        render e as JSON
        return
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
      return date1.format("yyyy-MM-dd") == date2.format("yyyy-MM-dd")
    }

    def isSameTime(date1, date2) {
      if (date1 == null || date2 == null){ 
        return false
      }

      return date1.format("HH:mm:ss") == date2.format("HH:mm:ss")
    }

    def hasItem(order, item){
      if (!order.menuSelections) return false

      def hasItem = false
      order.menuSelections.each{ selection -> 
        if (selection.menuItem.id == item.id) {
          hasItem = true
          return
        }
      }

      return hasItem
    }

    def hasSnackItem(order, item, snackIndex){
      if (!order.menuSelections) return false

      def hasItem = false
      order.menuSelections.each{ selection -> 
        if (selection.menuItem.id == item.id && selection.snackIndex == snackIndex) {
          hasItem = true
          return
        }
      }

      return hasItem
    }

    def lastPickup(order){
      order.orderPickups.sort({ a, b -> a.pickupDate<=>b.pickupDate}).last()
    }

    def hasItemOption(order, item, option){
      if (!order.menuSelections) return false

      def hasItem = false
      order.menuSelections.each{ selection -> 
        if (selection.menuItem.id == item.id) {
          selection.menuItemOptions.each{ opt -> 
            if (opt.id == option.id) {
              hasItem = true
              return
            }
          }
        }
      }

      return hasItem
    }

    def locationAvailable(availableLocations, location) {
      def ids = (availableLocations ?: []).collect{ l -> return l.id }
      return ids.indexOf( location.id ) == -1
    }
}

class OrderValidator {
  Map errors
  Map errorMsgs

  OrderValidator() {
    this.errors = [
      "pickupDate": [],
      "pickupTime": [],
      "diningHall": [],
      "breakfast": [],
      "sandwiches-salads": [],
      "beverages": [],
      "snacks": []
    ]

    this.errorMsgs = [
      "NO_PICKUP_DATE": "You must select a pickup date",
      "NO_PICKUP_TIME": "You must select a pickup time",
      "NO_DINING_HALL": "You must select a dining hall",
      "DINING_HALL_CLOSED": "You pickup location is closed during on a selected pickup date",
      "DINING_HALL_NOT_ALLOWED": "You do not have access to your selected location",
      "NO_BREAKFAST_ITEM": "You must select at least one breakfast item",
      "TOO_MANY_BREAKFAST_ITEMS": "You must select a maximum of 4 breakfast items",
    ]
  }

  def validateDateTimes(order) {
    println order

    if (order.orderPickups == null || order.orderPickups.size() == 0){
      this.errors["pickupDate"].push(this.errorMsgs["NO_PICKUP_DATE"])
      this.errors["pickupTime"].push(this.errorMsgs["NO_PICKUP_TIME"])
      return
    }

    order.orderPickups.each{ pickup -> 
      if (pickup.pickupDate == null) {
        this.errors["pickupDate"].push(this.errorMsgs["NO_PICKUP_DATE"])
      }

      if (pickup.pickupTime == null) {
        this.errors["pickupTime"].push(this.errorMsgs["NO_PICKUP_TIME"])
      }

      if (this.errors["pickupDate"].size() > 0 || this.errors["pickupTime"].size() > 0) {
        return
      }
    }
  }

  def validateDiningHall(order) {
    if (order.diningHall == null) {
      this.errors["diningHall"].push(this.errorMsgs["NO_DINING_HALL"])
    } else {
      order.orderPickups.each{ pickup -> 
        if (pickup.pickupDate < order.diningHall.openingDate || pickup.pickupDate > order.diningHall.closingDate){
          owner.errors["diningHall"].push(owner.errorMsgs["DINING_HALL_CLOSED"])
          return
        }
      } 

      // def userDiningHalls = order.user.diningHalls.collect{ dh -> return dh.id } 
      // if (userDiningHalls.indexOf(order.diningHall.id) == -1){
      //   this.errors["diningHall"].push(this.errorMsgs["DINING_HALL_NOT_ALLOWED"])
      // }
    }
  }

  def validate(meal, order) {
    switch (meal.name){
      case "Breakfast":
        this.validateBreakfast(order)
        break
      case "Lunch":
        this.validateLunch(order)
        break
      case "Dinner":
        this.validateDinner(order)
        break
      default:
        break
    }
  }

  def validateBreakfast(order) {
    this.validateDateTimes(order)
    this.validateDiningHall(order)

    if (order.menuSelections == null || order.menuSelections.size() == 0){
      this.errors['breakfast'].push(this.errorMsgs["NO_BREAKFAST_ITEM"])
      return
    }

    if (order.menuSelections == null || order.menuSelections.size() > 4){
      this.errors["breakfast"].push(this.errorMsgs["TOO_MANY_BREAKFAST_ITEMS"])
    }


  } 

  def validateLunch(order) {
    this.validateDateTimes(order)
    this.validateDiningHall(order)

    if (order.menuSelections == null || order.menuSelections.size() == 0){
      this.errors['breakfast'].push(this.errorMsgs["NO_BREAKFAST_ITEM"])
    }
  }

  def validateDinner(order){
    this.validateDateTimes(order)
    this.validateDiningHall(order)

    if (order.menuSelections == null || order.menuSelections.size() == 0){
      this.errors['breakfast'].push(this.errorMsgs["NO_BREAKFAST_ITEM"])
    }
  }

  def valid() {
    def isValid = true

    this.errors.each{ k, v ->
      if (v.size() > 0) {
        isValid = false
        return
      }
    }

    println isValid
    println "VALID"

    return isValid
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

    def createMenuSelection(item, options, snackIndex = 0) {
      if (item != null) {
        def selection = new MenuSelection(menuItem: item, snackIndex: snackIndex)
      
        options.each{ option -> 
          selection.addToMenuItemOptions(option)
        }
        
        return selection
      } else {
        return null
      }
    }

    def getBreakfast(section) {
      def selections = []
      def mains = this.getMenuItem("section." + section.id + ".menuItems")
      
      mains.each{ main -> 
        def options = []

        if (main != null && main.menuItemOptionGroups.size() > 0){
          main.menuItemOptionGroups.each{ group -> 
            def option = this.getOptions("group." + group.id + ".menuItem." + main.id)

            if (option.size() > 0) {
              options.push(option[0])
            }
          }
        }

        def selection = this.createMenuSelection(main, options)
        selections.push(selection)
      }

      println selections
      return selections
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

      return this.createMenuSelection(main, options, snackIndex)
    }
}

