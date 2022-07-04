package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String DESTINATION =
            "src/test/resources/writerTestFile.csv";
    private static final String EMPTY_TEST_FILE =
            "src/test/resources/emptyTestFile.csv";
    private static final String TEST_FILE =
            "src/test/resources/testFile.csv";
    private static final String report =
                    "type,fruit,quantity"
                    + LINE_SEPARATOR
                    + "b,pineapple,10"
                    + LINE_SEPARATOR
                    + "b,watermelon,87"
                    + LINE_SEPARATOR
                    + "b,lemon,30";
    private static Writer writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterImpl();
    }

    @Test
    public void writeEmpty_ok() {
        writer.writeDataToFile("", DESTINATION);
        List<String> expected = getDataFromFile(EMPTY_TEST_FILE);
        List<String> actual = getDataFromFile(DESTINATION);
        assertEquals(expected, actual);
    }

    @Test
    public void writeValidReport_ok() {
        writer.writeDataToFile(report, DESTINATION);
        List<String> expected = getDataFromFile(TEST_FILE);
        List<String> actual = getDataFromFile(DESTINATION);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToInvalidFilePath_notOk() {
        writer.writeDataToFile(report, "");
    }

    private List<String> getDataFromFile(String fileName) {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + fileName);
        }
    }
}
