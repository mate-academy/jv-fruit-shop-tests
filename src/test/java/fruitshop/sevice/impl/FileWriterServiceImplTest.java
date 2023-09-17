package fruitshop.sevice.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.sevice.FileWriterService;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private FileWriterService fileWriterService = new FileWriterServiceImpl();

    @Test
    void writeToFile_invalidDirectory_notOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,40";
        assertThrows(RuntimeException.class,
                () -> fileWriterService.writeToFile(report, "src/test/resources/.."));
    }

    @Test
    void writeToFile_validDirectory_ok() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,40";
        assertDoesNotThrow(
                () -> fileWriterService.writeToFile(report, "src/test/resources/report.csv"));
    }
}
