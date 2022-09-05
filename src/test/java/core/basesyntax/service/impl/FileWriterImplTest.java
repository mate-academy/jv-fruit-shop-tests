package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileWriterImplTest {
    private static final String TEST_FILE = "src/test/resources/test_output.csv";
    private static FileWriter writer;
    private Path path;

    @BeforeClass
    public static void beforeClass() {
        writer = new FileWriterImpl();
    }

    @Before
    public void setUp() {
        path = Path.of(TEST_FILE);
    }

    @Test
    public void writeData_Ok() {
        String actual = "fruit,quantity\n" +  "banana,152\n" + "apple,90\n" + "путін хуйло";
        writer.writeToFile(actual, TEST_FILE);
        String expected = readFromFile();
        assertTrue(Files.exists(path));
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeDataToEmptyPath_NotOK() {
        writer.writeToFile("hello world", "");
    }

    @Test (expected = RuntimeException.class)
    public void writeNullData_NotOK() {
        writer.writeToFile(null, TEST_FILE);
    }

    @After
    public void afterEachTest() {
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't delete a file " + path, e);
        }
    }

    private String readFromFile() {
        try {
            return String.join("\n", Files.readAllLines(Path.of(TEST_FILE)));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + TEST_FILE, e);
        }
    }
}
