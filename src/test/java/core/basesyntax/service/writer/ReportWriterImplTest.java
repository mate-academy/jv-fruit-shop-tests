package core.basesyntax.service.writer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriterImplTest {
    private static final String NOT_EXIST_FILE_PATH = "src/test/resources/test_file.csv";
    private static final String EMPTY_PATH = "";
    private static String report;
    private static ReportWriter reportWriter;

    @BeforeClass
    public static void beforeClass() {
        report = "Data for check writeReport method";
        reportWriter = new ReportWriterImpl();
    }

    @Test
    public void writeReport_writeData_notExistFile_ok() throws IOException {
        String expected = report;
        reportWriter.writeReport(NOT_EXIST_FILE_PATH, report);
        String actual = Files.readString(Path.of(NOT_EXIST_FILE_PATH));
        assertEquals(expected, actual);
        Files.delete(Path.of(NOT_EXIST_FILE_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_writeData_emptyPath_notOk() {
        reportWriter.writeReport(EMPTY_PATH, report);
    }
}
