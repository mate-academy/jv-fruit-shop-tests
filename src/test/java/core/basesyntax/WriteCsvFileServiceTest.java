package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exceptions.WriteFileException;
import core.basesyntax.service.WriteCsvFileService;
import core.basesyntax.service.implementations.WriteCsvFileServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    private static final String WRONG_EMPTY_FILENAME = " ";
    private static final String WRONG_SYMBOLS_FILENAME = "*?";
    private static WriteCsvFileService writeCsvFileService;
    private static String testReport;

    @BeforeEach
    void setUp() {
        writeCsvFileService = new WriteCsvFileServiceImpl();
        testReport = null;
    }

    @Test
    void wrongFileNameNotOkay() {
        StringBuilder report = new StringBuilder();
        report.append(REPORT_HEADER)
                .append(System.lineSeparator());
        report.append(CHERRY)
                .append(COMMA)
                .append(CHERRY_QUANTITY)
                .append(System.lineSeparator());
        report.append(DOPPELGANGER)
                .append(COMMA)
                .append(DOPPELGANGER_QUANTITY)
                .append(System.lineSeparator());
        testReport = report.toString();
        assertThrows(WriteFileException.class,
                () -> writeCsvFileService.writeFile(WRONG_EMPTY_FILENAME, testReport));
        assertThrows(WriteFileException.class,
                () -> writeCsvFileService.writeFile(WRONG_SYMBOLS_FILENAME, testReport));
    }

    @Test
    void isReportPresentOkay() {
        StringBuilder report = new StringBuilder();
        report.append(REPORT_HEADER)
                .append(System.lineSeparator());
        report.append(CHERRY)
                .append(COMMA)
                .append(CHERRY_QUANTITY)
                .append(System.lineSeparator());
        report.append(DOPPELGANGER)
                .append(COMMA)
                .append(DOPPELGANGER_QUANTITY)
                .append(System.lineSeparator());
        testReport = report.toString();
        writeCsvFileService.writeFile(REPORT_FILE, testReport);
        assertTrue(Files.exists(REPORT_FILE_PATH),
                "Can`t read " + REPORT_FILE + " file.");
    }

    @Test
    void writeEmptyReportNotOkay() {
        assertThrows(WriteFileException.class,
                () -> writeCsvFileService.writeFile(REPORT_FILE, testReport));
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
}
