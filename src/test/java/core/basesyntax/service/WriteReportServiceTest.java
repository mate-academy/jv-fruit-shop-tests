package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.impl.WriteReportServiceImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class WriteReportServiceTest {
    private static final String FILE_PATH = "src/test/resources/testInputReport.txt";
    private static final String REPORT = "This is report!";
    private final WriteReportService writeReportService = new WriteReportServiceImpl();

    @Test
    void writeReport_Ok() {

        writeReportService.writeReport(REPORT, FILE_PATH);

        String actualReport;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            actualReport = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Can't get data from file " + FILE_PATH, e);
        }

        assertEquals(REPORT, actualReport, "Report value must be - " + REPORT);
    }

    @Test
    public void writeReport_InvalidPath_NotOk() {
        String invalidPath = "non_existent_folder/test_report.txt";

        assertThrows(RuntimeException.class,
                () -> writeReportService.writeReport(REPORT, invalidPath),
                "Test failed! Exception should be thrown if path is invalid");
    }

    @Test
    public void writeReport_pathIsNull_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writeReportService.writeReport(REPORT, null),
                "Test failed! Exception should be thrown if path is Null");
    }

    @Test
    void writeReport_NullReport_NotOk() {
        assertThrows(RuntimeException.class,
                () -> writeReportService.writeReport(null, FILE_PATH),
                "Test failed! Exception should be thrown if Report is Null");
    }
}
