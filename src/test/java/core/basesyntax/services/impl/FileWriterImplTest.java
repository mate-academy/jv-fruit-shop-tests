package core.basesyntax.services.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.services.interfaces.FileWriter;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String PATH_TO_FILE = "src/test/java/core/basesyntax/sources/"
            + "test_write.csv";
    private static final String WRONG_PATH = "src/test/java/sources/test_write.csv";
    private static final String DATA_TO_WRITE = "Hello, world!";
    private FileWriter fileWriter;

    @Before
    public void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void test_writeToFile() {
        boolean actual = fileWriter.write(DATA_TO_WRITE, PATH_TO_FILE);
        assertTrue(actual);
    }

    @Test(expected = RuntimeException.class)
    public void test_writeToFileWithWrongPath() {
        fileWriter.write(DATA_TO_WRITE, WRONG_PATH);
    }
}
