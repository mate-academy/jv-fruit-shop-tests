package service;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String PATH_FROM_FILE = "src/test/resources/testWriter.csv";
    private static List<String> actual;

    @BeforeClass
    public static void operationBeforeTest() {
        actual = new ArrayList<>();
    }

    @After
    public void operationAfterTest() {
        actual.clear();
    }

    @Test
    public void writeToFile_validPath_Ok() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : linesFile()) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        String lines = stringBuilder.toString();
        FileWriter writeLineToFile = new FileWriterImpl();
        writeLineToFile.writeToFile(lines, PATH_FROM_FILE);
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_FROM_FILE))) {
            String line = reader.readLine();
            while (line != null) {
                actual.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file" + PATH_FROM_FILE, e);
        }
        assertEquals(linesFile(), actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_notValidPath_NotOk() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : linesFile()) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        String lines = stringBuilder.toString();
        FileWriter writeLineToFile = new FileWriterImpl();
        writeLineToFile.writeToFile(lines, "");
    }

    private List<String> linesFile() {
        List<String> testList = new ArrayList<>();
        testList.add("fruit,quantity");
        testList.add("banana,152");
        testList.add("apple,90");
        return testList;
    }
}
