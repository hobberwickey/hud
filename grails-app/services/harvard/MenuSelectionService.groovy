package harvard

import grails.gorm.services.Service

@Service(MenuSelection)
interface MenuSelectionService {

    MenuSelection get(Serializable id)

    List<MenuSelection> list(Map args)

    Long count()

    void delete(Serializable id)

    MenuSelection save(MenuSelection menuSelection)
}
