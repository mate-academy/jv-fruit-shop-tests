package core.basesyntax.services.imps;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.services.WriterService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImpTest {
    private static WriterService writerService;
    private static final String REPORT_FILE_PATH = "src/test/resources/report_file_test.csv";

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImp();
    }

    @Test
    void writeToFile_validFile_ok() {
        assertDoesNotThrow(() -> writerService.writeToFile(REPORT_FILE_PATH, "report"));
    }

    @Test
    void writeToFile_filePathNotFound_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeToFile("invalid/file/path.csv", "report"),
                "Should throw runtime exception.");
    }
}
