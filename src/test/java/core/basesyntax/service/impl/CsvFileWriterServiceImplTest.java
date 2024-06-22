package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.CsvFileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class CsvFileWriterServiceImplTest {
    private final CsvFileWriterService csvFileWriterService = new CsvFileWriterServiceImpl();

    @Test
    void writeToFile_validData_success() throws IOException {
        String filePath = "src/test/resources/test_output.csv";
        String data = "fruit,quantity\napple,10\nbanana,20";
        csvFileWriterService.writeToFile(data, filePath);
        assertTrue(Files.exists(Paths.get(filePath)));
    }

    @Test
    void writeToFile_invalidPath_throwsException() {
        assertThrows(IOException.class, () -> csvFileWriterService.writeToFile("data", "invalid/path/to/file.csv"));
    }
}
