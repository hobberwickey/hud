package harvard

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class DiningHallServiceSpec extends Specification {

    DiningHallService diningHallService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new DiningHall(...).save(flush: true, failOnError: true)
        //new DiningHall(...).save(flush: true, failOnError: true)
        //DiningHall diningHall = new DiningHall(...).save(flush: true, failOnError: true)
        //new DiningHall(...).save(flush: true, failOnError: true)
        //new DiningHall(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //diningHall.id
    }

    void "test get"() {
        setupData()

        expect:
        diningHallService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<DiningHall> diningHallList = diningHallService.list(max: 2, offset: 2)

        then:
        diningHallList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        diningHallService.count() == 5
    }

    void "test delete"() {
        Long diningHallId = setupData()

        expect:
        diningHallService.count() == 5

        when:
        diningHallService.delete(diningHallId)
        sessionFactory.currentSession.flush()

        then:
        diningHallService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        DiningHall diningHall = new DiningHall()
        diningHallService.save(diningHall)

        then:
        diningHall.id != null
    }
}
