import spock.lang.*

import static org.hamcrest.CoreMatchers.equalTo

@Narrative("""
As a user of 'Hello Word'
I want to say hello to everyone that run this application
""")
@See("http://confluence")
@Issue("http://jira/FOO-1")
@Title("Say hello to everybody")
class HelloWordBDDTest extends Specification {

    def "Say Hello to everybody"() {
        String result;
        given: 'New instance of application'
        HelloWord helloWord = new HelloWord();
        when: 'I dont enter any name'
        result = helloWord.say();
        then: 'Say Hello to everybody'
        result equalTo("hello");
    }

    def "Say Hello to everyone"() {
        String result;
        given: 'New instance of application'
        HelloWord helloWord = new HelloWord();
        when: 'I enter some name'
        result = helloWord.say("Gomes Bolanos");
        then: 'Say Hello to Gomes Bolanos'
        result equalTo("hello Gomes Bolanos");
    }

    def "Say Hello to everyone with error"() {
        String result;
        given: 'New instance of application'
        HelloWord helloWord = new HelloWord();
        when: 'I enter some name'
        result = helloWord.say("Gomes Bolanos");
        then: 'Say Hello to Gomes Bolanos'
        result equalTo("hello");
    }

    def "Say hello to each of them"(String name, String result) {
        given: 'New instance of application'
        HelloWord helloWord = new HelloWord();
        expect:
        helloWord.say(name) == result;
        where:
        name        | result
        "Gomes"     | "hello Gomes"
        "Antonieta" | "hello Antonieta"
    }


}
