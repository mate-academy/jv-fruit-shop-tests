package core.basesyntax;

import core.basesyntax.service.CsvWriterService;
import core.basesyntax.service.impl.CsvWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvWriterServiceTest {
    private static final Path TEST_REPORT_FILE_PATH =
            Path.of("src/test/resources/report_test.csv");
    private static String testReport;
    private static CsvWriterService csvWriterService;

    @BeforeClass
    public static void initCsvWriterService() {
        csvWriterService = new CsvWriterServiceImpl();
    }

    @BeforeClass
    public static void readTestReport() {
        try {
            testReport = Files.readString(TEST_REPORT_FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException("Could not read test report from file : "
                    + TEST_REPORT_FILE_PATH + ".", e);
        }
    }

    @Test
    public void write_dataMatch_Ok() {
        try {
            Files.deleteIfExists(TEST_REPORT_FILE_PATH);
            csvWriterService.write(TEST_REPORT_FILE_PATH, testReport);
            Assert.assertEquals(testReport, Files.readString(TEST_REPORT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file. Or delete file" + e);
        }
    }

    @Test (expected = RuntimeException.class)
    public void write_wrongPath_notOk() {
        csvWriterService.write(Path.of("/sr^c/tes:*t/res%ources/te*st.dat"), testReport);
    }
}
