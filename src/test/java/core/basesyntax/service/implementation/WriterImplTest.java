package core.basesyntax.service.implementation;

import core.basesyntax.service.Writer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WriterImplTest {
    private Writer writer;

    @Before
    public void setUp() {
        writer = new WriterImpl();
    }

    @After
    public void tearDown() {
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidFilePath_notOk() {
        writer.writeToFile("", "Some_report");
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullInputValue_notOk() {
        writer.writeToFile("", null);
    }
}
