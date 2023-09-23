package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriterService;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static FileWriterService fileWriterService;

    @BeforeAll
    static void beforeAll() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void writer_ValidReportFile_Ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        assertDoesNotThrow(() -> fileWriterService
                 .writeToFile(report,"src/test/resources/validReportFile.csv"));
    }

    @Test
    void writer_NonExistingReportFile_notOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        String actual = "src/test/resources///";
        assertThrows(
                RuntimeException.class, () -> fileWriterService.writeToFile(report, actual));
    }
}
