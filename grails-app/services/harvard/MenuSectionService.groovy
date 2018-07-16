package harvard

import grails.gorm.services.Service

@Service(MenuSection)
interface MenuSectionService {

    MenuSection get(Serializable id)

    List<MenuSection> list(Map args)

    Long count()

    void delete(Serializable id)

    MenuSection save(MenuSection menuSection)

}