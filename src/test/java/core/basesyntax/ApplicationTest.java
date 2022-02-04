package core.basesyntax;

import junit.framework.TestCase;

public class ApplicationTest extends TestCase {

    public void test() {
        Application application = new Application();
        String actual = application.method("Hello");
        assertEquals("Hello", actual);
    }
}
