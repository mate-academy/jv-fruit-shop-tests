package service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FileWriter;

public class FileWriterImplTest {
    private static FileWriter writer;
    private static final String ENTER = System.lineSeparator();
    private static final String TEST_FILE = "src/test/java/resources/testFile.csv";

    @BeforeClass
    public static void beforeClass() {
        writer = new FileWriterImpl();
    }

    @Test
    public void writeToFile_Ok() {
        String actual = "fruit,quantity" + ENTER + "banana,152" + ENTER + "apple,90" + ENTER;
        writer.writeToFile(actual, TEST_FILE);
        String expected = readFromFile();
        assertTrue(Files.exists(Path.of(TEST_FILE)));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_emptyPath_NotOK() {
        writer.writeToFile("hello world", "");
    }

    @Test(expected = RuntimeException.class)
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
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (String str : Files.readAllLines(Path.of(TEST_FILE))) {
                stringBuilder.append(str).append(ENTER);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
