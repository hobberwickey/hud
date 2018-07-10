package harvard

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class MenuItemTypeServiceSpec extends Specification {

    MenuItemTypeService menuItemTypeService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new MenuItemType(...).save(flush: true, failOnError: true)
        //new MenuItemType(...).save(flush: true, failOnError: true)
        //MenuItemType menuItemType = new MenuItemType(...).save(flush: true, failOnError: true)
        //new MenuItemType(...).save(flush: true, failOnError: true)
        //new MenuItemType(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //menuItemType.id
    }

    void "test get"() {
        setupData()

        expect:
        menuItemTypeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<MenuItemType> menuItemTypeList = menuItemTypeService.list(max: 2, offset: 2)

        then:
        menuItemTypeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        menuItemTypeService.count() == 5
    }

    void "test delete"() {
        Long menuItemTypeId = setupData()

        expect:
        menuItemTypeService.count() == 5

        when:
        menuItemTypeService.delete(menuItemTypeId)
        sessionFactory.currentSession.flush()

        then:
        menuItemTypeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        MenuItemType menuItemType = new MenuItemType()
        menuItemTypeService.save(menuItemType)

        then:
        menuItemType.id != null
    }
}
