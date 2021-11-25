package service;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter writer;

    @BeforeClass
    public static void operationBeforeTest() {
        writer = new FileWriterImpl();
    }

    @Test
    public void writeToFile_validPath_Ok() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : getExpectedResult()) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        String lines = stringBuilder.toString();
        String pathFromFile = "src/test/resources/testWriter.csv";
        writer.writeToFile(lines, pathFromFile);
        List<String> actual = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathFromFile))) {
            String line = reader.readLine();
            while (line != null) {
                actual.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file" + pathFromFile, e);
        }
        assertEquals(getExpectedResult(), actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_notValidPath_NotOk() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : getExpectedResult()) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        String lines = stringBuilder.toString();
        writer.writeToFile(lines, "");
    }

    private List<String> getExpectedResult() {
        List<String> testList = new ArrayList<>();
        testList.add("fruit,quantity");
        testList.add("banana,152");
        testList.add("apple,90");
        return testList;
    }
}
