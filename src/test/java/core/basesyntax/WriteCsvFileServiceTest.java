package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exceptions.WriteFileException;
import core.basesyntax.service.WriteCsvFileService;
import core.basesyntax.service.implementations.WriteCsvFileServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WriteCsvFileServiceTest {
    private static final String REPORT_FILE = "src/test/resources/report.csv";
    private static final Path REPORT_FILE_PATH = Path.of("src/test/resources/report.csv");
    private static final String CHERRY = "cherry";
    private static final int CHERRY_QUANTITY = 100;
    private static final String DOPPELGANGER = "doppelganger";
    private static final int DOPPELGANGER_QUANTITY = 50;
    private static final String REPORT_HEADER = "fruit,quantity";
    private static final String COMMA = ",";
    private static WriteCsvFileService writeCsvFileService;

    @BeforeAll
    static void setWriteCsvFileService() {
        writeCsvFileService = new WriteCsvFileServiceImpl();
    }

    @Test
    void writeFile_isReportPresent_okay() {
        String testReport = buildTestReport();
        writeCsvFileService.writeFile(REPORT_FILE, testReport);
        assertTrue(Files.exists(REPORT_FILE_PATH),
                "Can`t read " + REPORT_FILE + " file.");
        List<String> acquiredReport;
        try {
            acquiredReport = Files.readAllLines(REPORT_FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String acquiredReportStr = convertListDataToString(acquiredReport);
        assertEquals(testReport, acquiredReportStr);
    }

    @Test
    void writeFile_nullReport_notOkay() {
        assertThrows(WriteFileException.class,
                () -> writeCsvFileService.writeFile(REPORT_FILE, null));
    }

    @AfterEach
    void onTearDown() {
        try {
            if (Files.exists(REPORT_FILE_PATH)) {
                Files.delete(REPORT_FILE_PATH);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can`t delete file");
        }
    }

    private String buildTestReport() {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append(REPORT_HEADER)
                .append(System.lineSeparator());
        reportBuilder.append(CHERRY)
                .append(COMMA)
                .append(CHERRY_QUANTITY)
                .append(System.lineSeparator());
        reportBuilder.append(DOPPELGANGER)
                .append(COMMA)
                .append(DOPPELGANGER_QUANTITY);
        return reportBuilder.toString();
    }

    private String convertListDataToString(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i));
            if (!(i == list.size() - 1)) {
                builder.append(System.lineSeparator());
            }
        }
        return builder.toString();
    }
}
