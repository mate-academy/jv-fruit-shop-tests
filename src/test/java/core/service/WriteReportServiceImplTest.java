package core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.exception.ReportWriteException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriteReportServiceImplTest {
    private static final String TEST_FILE_PATH = "src/test/java/resources/test_report.csv";
    private WriteReportServiceImpl writeReportService;

    @BeforeEach
    public void setUp() {
        writeReportService = new WriteReportServiceImpl();
    }

    @Test
    public void testCreateReport_SuccessfulWrite() throws IOException {
        String reportData = "Apple,200" + "\n"
                + "Orange,300";
        writeReportService.createReport(TEST_FILE_PATH, reportData);

        assertTrue(Files.exists(Paths.get(TEST_FILE_PATH)));
        assertReportContentMatches(reportData);
    }

    @Test
    public void testCreateReport_ReportWriteException() {
        String invalidFilePath = "invalid/path/to/report.csv";
        String reportData = "Apple,200\nOrange,300";

        assertThrows(ReportWriteException.class, () ->
                writeReportService.createReport(invalidFilePath, reportData));
    }

    private void assertReportContentMatches(String expectedContent) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                if (lineCount == 0) {
                    assertEquals("fruit,quantity", line);
                } else {
                    assertEquals(expectedContent.split("\n")[lineCount - 1], line);
                }
                lineCount++;
            }
            assertEquals(expectedContent.split("\n").length, lineCount - 1);
        }
    }
}
