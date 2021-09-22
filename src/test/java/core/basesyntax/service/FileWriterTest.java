package core.basesyntax.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static final String CORRECT_PATH = "src/test/resources/testOutput.csv";
    private static final String EMPTY_PATH = "";
    private static FileWriter fileWriter;
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeAll() {
        fileWriter = new FileWriterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test
    public void write_writeFile_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("banana,107");
        expected.add("apple,100");
        fileWriter.write(expected, CORRECT_PATH);
        List<String> actual = fileReader.read(CORRECT_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void write_writeEmptyPath_NotOk() {
        List<String> testData = new ArrayList<>();
        testData.add("banana,107");
        testData.add("apple,100");
        fileWriter.write(testData, EMPTY_PATH);
    }
}
