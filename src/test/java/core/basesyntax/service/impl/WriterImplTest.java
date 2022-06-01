package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.Reader;
import core.basesyntax.service.Writer;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterImplTest {
    private static final String DESTINATION_FILE =
            "src/test/java/core/basesyntax/resources/writerTestFile.csv";
    private static final String EMPTY_FILE =
            "src/test/java/core/basesyntax/resources/emptyFile.csv";
    private static final String TEST_FILE =
            "src/test/java/core/basesyntax/resources/testFile.csv";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static String report;
    private static Writer writer;
    private static Reader reader;

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterImpl();
        reader = new ReaderImpl();
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
        List<String> expected = reader.readFromFile(EMPTY_FILE);
        List<String> actual = reader.readFromFile(DESTINATION_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void writeReport_ok() {
        writer.writeToFile(report, DESTINATION_FILE);
        List<String> expected = reader.readFromFile(TEST_FILE);
        List<String> actual = reader.readFromFile(DESTINATION_FILE);
        assertEquals(expected, actual);
    }
}

