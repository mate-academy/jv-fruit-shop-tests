package core.basesyntax.service;

import core.basesyntax.service.impl.WriterServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private static WriterService writerService;
    private static final String REPORT = "banana,152" + System.lineSeparator() + "apple,90";
    private static final String FILE_PATH = "src/main/resources/test_report.cvs";

    @BeforeAll
    static void beforeAll() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeToFile_bothArgumentsCorrect_Ok() {
        Assertions.assertDoesNotThrow(() -> writerService.writeToFile(FILE_PATH, REPORT));
    }

    @Test
    void writeToFile_bothArgumentsNull_notOk() {
        Assertions.assertThrows(RuntimeException.class,() -> writerService.writeToFile(null, null));
    }

    @Test
    void writeToFile_bothArgumentsEmpty_notOk() {
        Assertions.assertThrows(RuntimeException.class,() -> writerService.writeToFile("", ""));
    }

    @Test
    void writeToFile_filePathNull_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(null, REPORT));
    }

    @Test
    void writeToFile_filePathEmpty_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> writerService.writeToFile("", REPORT));
    }

    @Test
    void writeToFile_filePathWhiteSpace_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(" ", REPORT));
    }

    @Test
    void writeToFile_reportNull_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> writerService.writeToFile(FILE_PATH, null));
    }

    @Test
    void writeToFile_reportEmpty_Ok() {
        Assertions.assertDoesNotThrow(() -> writerService.writeToFile(FILE_PATH, ""));
    }

    @Test
    void writeToFile_reportWhiteSpace_Ok() {
        Assertions.assertDoesNotThrow(() -> writerService.writeToFile(FILE_PATH, " "));
    }
}
