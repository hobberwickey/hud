package harvard

import grails.gorm.services.Service

@Service(DiningHall)
interface DiningHallService {

    DiningHall get(Serializable id)

    List<DiningHall> list(Map args)

    Long count()

    void delete(Serializable id)

    DiningHall save(DiningHall diningHall)

}