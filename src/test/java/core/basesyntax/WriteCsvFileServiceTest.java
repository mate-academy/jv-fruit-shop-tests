package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exceptions.WriteFileException;
import core.basesyntax.service.WriteCsvFileService;
import core.basesyntax.service.implementations.WriteCsvFileServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriteCsvFileServiceTest {
    private static final String REPORT_FILE = "src/test/resources/report.csv";
    private static final Path REPORT_FILE_PATH = Path.of("src/test/resources/report.csv");
    private static WriteCsvFileService writeCsvFileService;
    private static List<String> testReport;

    @BeforeEach
    void setUp() {
        writeCsvFileService = new WriteCsvFileServiceImpl();
        testReport = new ArrayList<>();
    }

    @Test
    void wrongFileNameNotOkay() {
        testReport.add("fruit,quantity");
        testReport.add("cherry,100");
        testReport.add("doppelganger,50");
        String testFileName1 = " ";
        String testFileName2 = "*?";
        assertThrows(WriteFileException.class,
                () -> writeCsvFileService.writeFile(testFileName1, testReport));
        assertThrows(WriteFileException.class,
                () -> writeCsvFileService.writeFile(testFileName2, testReport));
    }

    @Test
    void isReportPresentOkay() {
        testReport.add("fruit,quantity");
        testReport.add("cherry,100");
        testReport.add("doppelganger,50");
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
