package core.basesyntax.service.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_REPORT_FILE_PATH = "src/test/resources/testReport.csv";
    private static final String HEADER = "fruit,quantity";
    private static final String COMMA = ",";
    private FileWriter fileWriter;

    @BeforeEach
    public void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void valid_Data_Ok() {
        String resultingReport = HEADER + System.lineSeparator()
                + "apple" + COMMA + "34"
                + System.lineSeparator()
                + "banana" + COMMA + "24";

        fileWriter.write(resultingReport, TEST_REPORT_FILE_PATH);

        FileReader fileReader = new FileReaderImpl(TEST_REPORT_FILE_PATH);
        List<String> list = fileReader.read();

        String actual = String.join(System.lineSeparator(), list);

        assertEquals(resultingReport, actual);
    }

    @Test
    public void resultingReport_IsNull_NotOk() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileWriter.write(null, TEST_REPORT_FILE_PATH));
        assertEquals("can not handle null value", exception.getMessage());
    }

    @Test
    public void reportFilePath_IsNull_NotOk() {
        String resultingReport
                = HEADER + System.lineSeparator()
                + "apple" + COMMA + "34"
                + System.lineSeparator()
                + "banana" + COMMA + "24";

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileWriter.write(resultingReport, null));
        assertEquals("can not handle null value", exception.getMessage());
    }

    @Test
    public void invalidPath_NotOk() {
        String resultingReport
                = HEADER + System.lineSeparator()
                + "apple" + COMMA + "34"
                + System.lineSeparator()
                + "banana" + COMMA + "24";

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileWriter.write(resultingReport, ""));
        assertEquals("can't write data to file", exception.getMessage());
    }
}
