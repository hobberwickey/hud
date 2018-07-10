package harvard

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class MenuItemServiceSpec extends Specification {

    MenuItemService menuItemService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new MenuItem(...).save(flush: true, failOnError: true)
        //new MenuItem(...).save(flush: true, failOnError: true)
        //MenuItem menuItem = new MenuItem(...).save(flush: true, failOnError: true)
        //new MenuItem(...).save(flush: true, failOnError: true)
        //new MenuItem(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //menuItem.id
    }

    void "test get"() {
        setupData()

        expect:
        menuItemService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<MenuItem> menuItemList = menuItemService.list(max: 2, offset: 2)

        then:
        menuItemList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        menuItemService.count() == 5
    }

    void "test delete"() {
        Long menuItemId = setupData()

        expect:
        menuItemService.count() == 5

        when:
        menuItemService.delete(menuItemId)
        sessionFactory.currentSession.flush()

        then:
        menuItemService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        MenuItem menuItem = new MenuItem()
        menuItemService.save(menuItem)

        then:
        menuItem.id != null
    }
}
