package harvard

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class MenuSectionServiceSpec extends Specification {

    MenuSectionService menuSectionService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new MenuSection(...).save(flush: true, failOnError: true)
        //new MenuSection(...).save(flush: true, failOnError: true)
        //MenuSection menuSection = new MenuSection(...).save(flush: true, failOnError: true)
        //new MenuSection(...).save(flush: true, failOnError: true)
        //new MenuSection(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //menuSection.id
    }

    void "test get"() {
        setupData()

        expect:
        menuSectionService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<MenuSection> menuSectionList = menuSectionService.list(max: 2, offset: 2)

        then:
        menuSectionList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        menuSectionService.count() == 5
    }

    void "test delete"() {
        Long menuSectionId = setupData()

        expect:
        menuSectionService.count() == 5

        when:
        menuSectionService.delete(menuSectionId)
        sessionFactory.currentSession.flush()

        then:
        menuSectionService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        MenuSection menuSection = new MenuSection()
        menuSectionService.save(menuSection)

        then:
        menuSection.id != null
    }
}
