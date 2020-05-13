import org.junit.Test;

import static org.junit.Assert.*;

public class HelloWordTest {

    @Test
    public void shouldSayHello(){
        HelloWord helloWord =new HelloWord();
        final String result = helloWord.say();
        assertSame(result, "hello");
    }

}