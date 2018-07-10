package harvard

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class MealServiceSpec extends Specification {

    MealService mealService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Meal(...).save(flush: true, failOnError: true)
        //new Meal(...).save(flush: true, failOnError: true)
        //Meal meal = new Meal(...).save(flush: true, failOnError: true)
        //new Meal(...).save(flush: true, failOnError: true)
        //new Meal(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //meal.id
    }

    void "test get"() {
        setupData()

        expect:
        mealService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Meal> mealList = mealService.list(max: 2, offset: 2)

        then:
        mealList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        mealService.count() == 5
    }

    void "test delete"() {
        Long mealId = setupData()

        expect:
        mealService.count() == 5

        when:
        mealService.delete(mealId)
        sessionFactory.currentSession.flush()

        then:
        mealService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Meal meal = new Meal()
        mealService.save(meal)

        then:
        meal.id != null
    }
}
