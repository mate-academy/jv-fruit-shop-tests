package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String TEST_FILE = "src/test/resources/test_output.csv";
    private static FileWriter writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new FileWriterImpl();
    }

    @Test
    public void writeToFile_Ok() {
        String actual = "fruit,quantity\n" + "banana,152\n" + "apple,90\n" + "путін хуйло";
        writer.writeToFile(actual, TEST_FILE);
        String expected = readFromFile();
        assertTrue(Files.exists(Path.of(TEST_FILE)));
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_emptyPath_NotOK() {
        writer.writeToFile("hello world", "");
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_nullData_NotOK() {
        writer.writeToFile(null, TEST_FILE);
    }

    @After
    public void afterEachTest() {
        Path path = Path.of(TEST_FILE);
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
