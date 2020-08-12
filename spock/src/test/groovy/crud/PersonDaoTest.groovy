package crud

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

import java.time.LocalDate

import static org.hamcrest.CoreMatchers.*
import static spock.util.matcher.HamcrestSupport.that

class PersonDaoTest extends Specification {

    def "test save"() {
        given:
        ObjectMapper mapper = new ObjectMapper()
        PersonDao personDao = new PersonDao(mapper)
        Person person = new Person("Gomes", "Bolanos")
        when:
        String result = personDao.save(person)

        then:
        that result, is(not(nullValue()))
    }

    def "test save mock"() {
        given:
        ObjectMapper mapper = Mock(ObjectMapper)
        PersonDao personDao = new PersonDao(mapper)
        Person person = new Person("Gomes", "Bolanos")
        when:
        String result = personDao.save(person)

        then:
        that result, is(not(nullValue()))
        and:
        1 * mapper.writeValueAsString(_ as Person)

    }

    def "test save stub"() {
        given:
        ObjectMapper mapper = Mock(ObjectMapper)
        1 * mapper.writeValueAsString(_ as Person) >> { "{fake: true}" }
        PersonDao personDao = new PersonDao(mapper)
        Person person = new Person("Gomes", "Bolanos")
        when:
        String result = personDao.save(person)

        then:
        that result, is(not(nullValue()))
    }

    def "test save validation"() {
        given:
        ObjectMapper mapper = Mock(ObjectMapper)
        PersonDao personDao = new PersonDao(mapper)
        Person person = new Person("", "Bolanos")
        when:
        personDao.save2(person)

        then:
        thrown(IllegalArgumentException)
    }

    def "test save validation 2"() {
        given:
        ObjectMapper mapper = Mock(ObjectMapper)
        PersonDao personDao = new PersonDao(mapper)
        Person person = new Person("", "Bolanos")
        when:
        personDao.save2(person)

        then:
        thrown(IllegalArgumentException)
    }

    def "test save validation 3"() {
        given:
        ObjectMapper mapper = Mock(ObjectMapper)
        PersonDao personDao = new PersonDao(mapper)
        Person person = new Person(firstName, "Bolanos")
        person.setBirthDate(birthDate)
        when:
        personDao.save2(person)
        success()

        then:
        thrown(exception)

        where:
        firstName | birthDate                    || exception
        ""        | null                         || IllegalArgumentException
        "Gomes"   | LocalDate.now().plusDays(1)  || RuntimeException
        "Gomes"   | LocalDate.now().minusDays(1) || Success

    }

    class Success extends Throwable {
    }

    void success() {
        throw new Success()
    }
}
