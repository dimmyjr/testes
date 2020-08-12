package crud


import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class PersonTest extends Specification {

    def setup() {
        LocalDate.metaClass.static.now = { return LocalDate.of(2000, 01, 01) };
    }

    def "test full Name"() {
        Person person = new Person("Gomes", "Bolanos")
        expect:
        person.fullName() == "Gomes Bolanos"
    }

    def "Calcular a idade do caboco"() {
        given:
        Person person = new Person("Gomes", "Bolanos")
        person.setBirthDate(LocalDate.of(1950, 1, 1))
        when:
        def result = person.getAge()
        then:
        result == 50

    }

    def "Calcular a idade do caboco 2"() {
        given:
        Person person = new Person("Gomes", "Bolanos")
        person.setBirthDate(LocalDate.of(1988, 2, 29))
        when:
        def resultAge = person.getAge()
        def resultGroup = person.getAgeGroup()
        then:
        resultAge == 12
        and:
        resultGroup == "JOVEM"
    }

    def "Calcular a idade do caboco 3"() {
        given:
        Person person = new Person("Gomes", "Bolanos")
        person.setBirthDate(birthdate)
        when:
        def resultAge = person.getAge()
        def resultGroup = person.getAgeGroup()
        then:
        resultAge == exceptAge
        and:
        resultGroup == exceptGroup

        where:
        birthdate                 | exceptAge | exceptGroup
        LocalDate.of(1988, 2, 29) | 12        | "JOVEM"
        LocalDate.of(1960, 2, 29) | 40        | "ADULTO"
        LocalDate.of(1940, 2, 29) | 60        | "IDOSO"
    }

    @Unroll
    def "Calcular a idade do caboco, Nascido em #birthdate, tenha #exceptAge and seja do groupo #exceptGroup"() {
        given: 'DADO a pessoa nascida em #birthdate'
//        def currentDate = LocalDate.of(2001, 2, 28)
        Person person = new Person("Gomes", "Bolanos")
        person.setBirthDate(birthdate)
        when: 'QUANDO hoje é #currentDate'
        def resultAge = person.getAge()
        def resultGroup = person.getAgeGroup()
        then: 'ENTAO a pessoa tem #exceptAge '
        resultAge == exceptAge
        and: 'E faz parte do grupo #exceptGroup'
        resultGroup == exceptGroup

        where:
        birthdate                 | exceptAge | exceptGroup
        LocalDate.of(1988, 2, 29) | 12        | "JOVEM"
        LocalDate.of(1960, 2, 29) | 40        | "ADULTO"
        LocalDate.of(1940, 2, 29) | 60        | "IDOSO"
    }

//    def "date"(){
//        LocalDate.now()
//        LocalDate.metaClass.static.now = {return LocalDate.of(2000,01,01)};
//        expect:
//        print LocalDate.now();
//    }

}
