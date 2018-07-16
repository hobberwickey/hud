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
  }
  
  def destroy = {
  
  }
}
