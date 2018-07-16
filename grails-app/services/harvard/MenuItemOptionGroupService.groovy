package harvard

import grails.gorm.services.Service

@Service(MenuItemOptionGroup)
interface MenuItemOptionGroupService {

    MenuItemOptionGroup get(Serializable id)

    List<MenuItemOptionGroup> list(Map args)

    Long count()

    void delete(Serializable id)

    MenuItemOptionGroup save(MenuItemOptionGroup menuItemOptionGroup)

}