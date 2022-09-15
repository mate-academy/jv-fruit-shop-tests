package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static final String REPORT_AFTER_DAY = "src/test/java/resources/ReportAfterDay.csv";
    private static final String INVALID_PATH = "";
    private static final String TEST_REPORT_FILE = "src/test/java/resources/TestReportFile.csv";
    private static final String EMPTY_FILE = "src/test/java/resources/EmptyFile.csv";
    private static final String SEPARATOR = System.lineSeparator();
    private static final String TITLE = "fruit,quantity";
    private static String reportAfterDay;
    private static FileWriter writer;
    private static FileReader reader;

    @BeforeClass
    public static void beforeClass() {
        writer = new FileWriterImpl();
        reader = new FileReaderImpl();
        StringBuilder stringBuilder = new StringBuilder();
        reportAfterDay = stringBuilder.append(TITLE)
                .append(SEPARATOR)
                .append("banana,40")
                .append(SEPARATOR)
                .append("apple,50")
                .toString();
    }

    @Test
    public void writeReport_Ok() {
        writer.write(reportAfterDay, REPORT_AFTER_DAY);
        List<String> expected = reader.read(TEST_REPORT_FILE);
        List<String> actual = reader.read(REPORT_AFTER_DAY);
        assertEquals(expected, actual);
    }

    @Test
    public void writeEmptyFile_Ok() {
        writer.write("", REPORT_AFTER_DAY);
        List<String> expected = reader.read(EMPTY_FILE);
        List<String> actual = reader.read(REPORT_AFTER_DAY);
        assertEquals(expected, actual);
    }

    @Test
    public void invalidPathToFile_NotOk() {
        try {
            writer.write(REPORT_AFTER_DAY, INVALID_PATH);
        } catch (Exception e) {
            return;
        }
        fail("Invalid path file: " + INVALID_PATH);
    }
}
