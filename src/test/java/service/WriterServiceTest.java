package service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.basesyntax.service.WriterService;
import core.basesyntax.basesyntax.service.impl.WriterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceTest {
    private static final String INVALID_FILE_PATH = "src/test/java/resources/empty.csv";
    private static final String VALID_FILE_PATH = "src/test/java/resources/report.csv";
    private static WriterService writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeDataToFile_nullPathAndData_notOk() {
        assertThrows(NullPointerException.class,
                () -> writerService.writeDataToFile(null, INVALID_FILE_PATH));
    }

    @Test
    void writeDataToFile_nullData_notOk() {
        assertThrows(NullPointerException.class,
                () -> writerService.writeDataToFile(null, VALID_FILE_PATH));
    }

    @Test
    void writeDataToFile_nullPath_notOk() {
        String data = "fruit,quantity\nbanana,20\napple,10";
        assertThrows(NullPointerException.class,
                () -> writerService.writeDataToFile(data, null));
    }

    @Test
    void writeDataToFile_validDataAndPath_ok() {
        String data = "fruit,quantity\nbanana,20\napple,10";
        writerService.writeDataToFile(data, VALID_FILE_PATH);
    }
}
