package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.WriteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriteServiceImplTest {
    private static WriteService writeService;
    private static final String MASSAGE = "Should throw runtime exception.";

    @BeforeAll
    public static void beforeAll() {
        writeService = new WriteServiceImpl();
    }

    @Test
    void write_writeToFile_Ok() {
        boolean excually =
                writeService.wrightToFile("src/main/java/core/basesyntax/files/report_file.csv");
        assertTrue(excually);
    }

    @Test
    void write_invalidFilePath_ExceptionThrown() {
        assertThrows(RuntimeException.class,
                () -> writeService.wrightToFile("invalid/file/path.csv"),
                MASSAGE);
    }
}
