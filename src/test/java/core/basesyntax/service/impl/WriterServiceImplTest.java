package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriterService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private static WriterService writeService;
    private static final String MASSAGE = "Should throw runtime exception.";

    @BeforeAll
    public static void beforeAll() {
        writeService = new WriterServiceImpl();
    }

    @Test
    void write_writeToFile_Ok() {
        boolean excually =
                writeService.writeToFile("src/main/resources/report_file.csv", "report");
        assertTrue(excually);
    }

    @Test
    void write_invalidFilePath_ExceptionThrown() {
        assertThrows(RuntimeException.class,
                () -> writeService.writeToFile("invalid/file/path.csv", "report"),
                MASSAGE);
    }
}
