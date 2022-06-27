package basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import basesyntax.service.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterImplTest {
    private static final String REPORT_AFTER_DAY =
            "src/test/java/resources/ReportAfterDay.csv";
    private static final String INVALID_PATH_FILE = "";
    private static final String TEST_REPORT_FILE =
            "src/test/java/resources/TestReportFile.csv";
    private static final String EMPTY_FILE =
            "src/test/java/resources/EmptyFile.csv";
    private static final String SPLITERATOR = System.lineSeparator();
    private static final String TITLE = "fruit,quantity";
    private static String reportAfterDay;
    private static Writer writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterImpl();
        StringBuilder stringBuilder = new StringBuilder();
        reportAfterDay = stringBuilder.append(TITLE)
                .append(SPLITERATOR)
                .append("banana,152")
                .append(SPLITERATOR)
                .append("apple,90")
                .toString();
    }

    @Test
    public void writeToFile_report_Ok() {
        writer.writeToFile(reportAfterDay, REPORT_AFTER_DAY);
        List<String> expected = readReportFile(TEST_REPORT_FILE);
        List<String> actual = readReportFile(REPORT_AFTER_DAY);
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_emptyReport_Ok() {
        writer.writeToFile("", REPORT_AFTER_DAY);
        List<String> expected = readReportFile(EMPTY_FILE);
        List<String> actual = readReportFile(REPORT_AFTER_DAY);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_invalidPathToFile_notOk() {
        writer.writeToFile(REPORT_AFTER_DAY, INVALID_PATH_FILE);
    }

    private List<String> readReportFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("File " + filePath + " not read.", e);
        }
    }
}
