package harvard

import grails.converters.JSON

class BootStrap {
  def init = { servletContext ->
    JSON.registerObjectMarshaller(Meal) {
      def returnArray = [:]
          returnArray['id'] = it.id
          returnArray['name'] = it.name
          
      return returnArray
    }

    JSON.registerObjectMarshaller(Menu) {
      def returnArray = [:]
          returnArray['id'] = it.id
          returnArray['name'] = it.name
          returnArray['meal'] = it.meal
          returnArray['menuSections'] = it.menuSections

      return returnArray
    }

    JSON.registerObjectMarshaller(MenuSection) {
      def returnArray = [:]
          returnArray['id'] = it.id
          returnArray['name'] = it.name
          returnArray['ordering'] = it.ordering
          returnArray['menuItems'] = it.menuItems
          
      return returnArray
    }

    JSON.registerObjectMarshaller(MenuItemOptionGroup) {
      def returnArray = [:]
          returnArray['id'] = it.id
          returnArray['name'] = it.name
          returnArray['ordering'] = it.ordering
          returnArray['menuItemOptions'] = it.menuItemOptions

      return returnArray
    }

    JSON.registerObjectMarshaller(MenuItemOption) {
      def returnArray = [:]
          returnArray['id'] = it.id
          returnArray['name'] = it.name

      return returnArray
    }

    JSON.registerObjectMarshaller(DiningHall) {
      def returnArray = [:]
          returnArray['id'] = it.id
          returnArray['name'] = it.name
          returnArray['openingDate'] = it.openingDate
          returnArray['closingDate'] = it.closingDate
          returnArray['status'] = it.status
          returnArray['menus'] = it.menus

      return returnArray
    }

    JSON.registerObjectMarshaller(User) {
      def returnArray = [:]
          returnArray['id'] = it.id
          returnArray['userType'] = it.userType
          returnArray['huid'] = it.huid
          returnArray['firstName'] = it.firstName
          returnArray['lastName'] = it.lastName
          returnArray['active'] = it.active
          returnArray['blocked'] = it.blocked
          returnArray['email'] = it.email
          returnArray['diningHalls'] = it.diningHalls

      return returnArray
    }

    JSON.registerObjectMarshaller(Orders) {
      def returnArray = [:]
          returnArray['id'] = it.id
          returnArray['createdOn'] = it.createdOn
          returnArray['updatedAt'] = it.updatedAt
          returnArray['menuSelections'] = it.menuSelections

      return returnArray
    }

    JSON.registerObjectMarshaller(MenuSelection) {
      def returnArray = [:]
          returnArray['id'] = it.id
          returnArray['orders'] = it.orders
          returnArray['menuItem'] = it.menuItem
          returnArray['menuItemOptions'] = it.menuItemOptions

      return returnArray
    }
  }
  
  def destroy = {
  
  }
}
