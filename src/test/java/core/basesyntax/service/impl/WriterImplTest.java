package core.basesyntax.service.impl;

import core.basesyntax.service.Writer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WriterImplTest {
    private static final String DESTINATION_FILE =
            "src/test/resources/writerTestFile.csv";
    private static final String EMPTY_FILE =
            "src/test/resources/emptyFile.csv";
    private static final String TEST_FILE =
            "src/test/resources/testFile.csv";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static String report;
    private static Writer writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterImpl();
        StringBuilder builder = new StringBuilder();
        report = builder.append("type,fruit,quantity")
                .append(LINE_SEPARATOR)
                .append("b,banana,20")
                .append(LINE_SEPARATOR)
                .append("b,apple,100")
                .append(LINE_SEPARATOR)
                .append("b,orange,20")
                .toString();
    }

    @Test(expected = RuntimeException.class)
    public void writeToNotValidFile_notOk() {
        writer.writeToFile(report, "");
    }

    @Test
    public void writeEmptyData_ok() {
        writer.writeToFile("", DESTINATION_FILE);
        List<String> expected = readFromFile(EMPTY_FILE);
        List<String> actual = readFromFile(DESTINATION_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void writeReport_ok() {
        writer.writeToFile(report, DESTINATION_FILE);
        List<String> expected = readFromFile(TEST_FILE);
        List<String> actual = readFromFile(DESTINATION_FILE);
        assertEquals(expected, actual);
    }

    private List<String> readFromFile (String fileName) {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + fileName);
        }
    }
}

