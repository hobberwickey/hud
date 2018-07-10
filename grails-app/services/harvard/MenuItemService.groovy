package harvard

import grails.gorm.services.Service

@Service(MenuItem)
interface MenuItemService {

    MenuItem get(Serializable id)

    List<MenuItem> list(Map args)

    Long count()

    void delete(Serializable id)

    MenuItem save(MenuItem menuItem)

}