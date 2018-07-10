package harvard

import grails.gorm.services.Service

@Service(MenuItemOption)
interface MenuItemOptionService {

    MenuItemOption get(Serializable id)

    List<MenuItemOption> list(Map args)

    Long count()

    void delete(Serializable id)

    MenuItemOption save(MenuItemOption menuItemOption)

}