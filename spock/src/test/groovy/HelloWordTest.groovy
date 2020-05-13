import spock.lang.Specification

class HelloWordTest extends Specification {

    def "Say Hello"() {
        HelloWord helloWord = new HelloWord();
        expect:
        helloWord.say() is("hello");
    }
}
