package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportWriterService;
import core.basesyntax.service.impl.ReportWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriterServiceImplTests {
    private static final String NON_EXISTENT_FOLDER = "src/main/report/report.csv";
    private static final String NORMAL_FILE_PATH = "src/main/resources/output.csv";
    private static ReportWriterService reportWriter;

    @BeforeClass
    public static void beforeClass() {
        reportWriter = new ReportWriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeReport_nonExistentFolder_notOk() {
        String expected = "report";
        reportWriter.writeReport(expected, NON_EXISTENT_FOLDER);
    }

    @Test
    public void writeReport_normalData_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,115" + System.lineSeparator()
                + "apple,110";
        reportWriter.writeReport(expected, NORMAL_FILE_PATH);
        String actual = readFile(NORMAL_FILE_PATH);
        assertEquals(expected, actual);
    }

    private String readFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + path, e);
        }
    }
}
