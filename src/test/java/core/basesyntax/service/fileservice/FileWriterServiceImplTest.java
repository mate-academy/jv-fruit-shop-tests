package core.basesyntax.service.fileservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FileWriterServiceImplTest {
    private static final String REPORT = """
            fruit,quantity
            banana,30
            lemon,50
            """;
    private static final String PATH_TO_FILE = "src/test/resources/finalReportTest.csv";
    private static final String INVALID_PATH_TO_FILE = "src/test/test/test/java.csv";
    private FileWriterService fileWriterService;

    @BeforeEach
    void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    void writeToValidFileName_ok() {
        fileWriterService.writeReportToFile(REPORT, PATH_TO_FILE);
    }

    @Test
    void writeToInvalidFileName_notOk() {
        assertThrows(RuntimeException.class, () -> fileWriterService.writeReportToFile(REPORT, INVALID_PATH_TO_FILE));
    }
}