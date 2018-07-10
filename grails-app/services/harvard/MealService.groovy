package harvard

import grails.gorm.services.Service

@Service(Meal)
interface MealService {

    Meal get(Serializable id)

    List<Meal> list(Map args)

    Long count()

    void delete(Serializable id)

    Meal save(Meal meal)

}