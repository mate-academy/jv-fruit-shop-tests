package core.basesyntax.service.fileservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static FileReader fileReaderService;
    private static final String VALID_FILE = "src/test/resources/validReportToRead.csv";
    private static final String NON_EXISTENT_FILE = "src/test/resources/fileNotExists.csv";
    private static final String EMPTY_FILE = "src/test/resources/emptyReportToRead.csv";
    private static final String INVALID_DATA_FILE = "src/test/resources/invalidReportToRead.csv";

    @BeforeAll
    static void beforeAll() {
        fileReaderService = new FileReaderImpl();
    }

    @Test
    void read_validInputFile_ok() {
        List<String> validReport = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> inputReport = fileReaderService.read(VALID_FILE);
        assertEquals(validReport, inputReport);
    }

    @Test
    void read_nonExistentFile_notOk() {
        String expectedMessage = "Can't get data from file " + NON_EXISTENT_FILE;
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReaderService.read(NON_EXISTENT_FILE));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void read_emptyFile_ok() {
        List<String> emptyReport = fileReaderService.read(EMPTY_FILE);
        assertTrue(emptyReport.isEmpty());
    }

    @Test
    void read_invalidDataFile_ok() {
        List<String> invalidReport = List.of("type,fruit,quantity", "20,banana",
                "100,apple", "30,banana");
        List<String> inputReport = fileReaderService.read(INVALID_DATA_FILE);
        assertEquals(invalidReport, inputReport);
    }

    @Test
    void read_nullPath_notOk() {
        assertThrows(NullPointerException.class,
                () -> fileReaderService.read(null));
    }
}
