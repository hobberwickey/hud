package harvard

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class MenuItemOptionGroupServiceSpec extends Specification {

    MenuItemOptionGroupService menuItemOptionGroupService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new MenuItemOptionGroup(...).save(flush: true, failOnError: true)
        //new MenuItemOptionGroup(...).save(flush: true, failOnError: true)
        //MenuItemOptionGroup menuItemOptionGroup = new MenuItemOptionGroup(...).save(flush: true, failOnError: true)
        //new MenuItemOptionGroup(...).save(flush: true, failOnError: true)
        //new MenuItemOptionGroup(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //menuItemOptionGroup.id
    }

    void "test get"() {
        setupData()

        expect:
        menuItemOptionGroupService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<MenuItemOptionGroup> menuItemOptionGroupList = menuItemOptionGroupService.list(max: 2, offset: 2)

        then:
        menuItemOptionGroupList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        menuItemOptionGroupService.count() == 5
    }

    void "test delete"() {
        Long menuItemOptionGroupId = setupData()

        expect:
        menuItemOptionGroupService.count() == 5

        when:
        menuItemOptionGroupService.delete(menuItemOptionGroupId)
        sessionFactory.currentSession.flush()

        then:
        menuItemOptionGroupService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        MenuItemOptionGroup menuItemOptionGroup = new MenuItemOptionGroup()
        menuItemOptionGroupService.save(menuItemOptionGroup)

        then:
        menuItemOptionGroup.id != null
    }
}
