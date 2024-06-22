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
        assertThrows(IOException.class, ()
                -> csvFileWriterService.writeToFile("data", "invalid/path/to/file.csv"));
    }

    @Test
    void writeToFile_emptyData_success() throws IOException {
        String filePath = "src/test/resources/empty_output.csv";
        String data = "";
        csvFileWriterService.writeToFile(data, filePath);
        assertTrue(Files.exists(Paths.get(filePath)));
    }

    @Test
    void writeToFile_largeData_success() throws IOException {
        String filePath = "src/test/resources/large_output.csv";
        StringBuilder data = new StringBuilder("fruit,quantity\n");
        for (int i = 0; i < 1000; i++) {
            data.append("apple,").append(i).append("\n");
        }
        csvFileWriterService.writeToFile(data.toString(), filePath);
        assertTrue(Files.exists(Paths.get(filePath)));
    }

    @Test
    void writeToFile_specialCharacters_success() throws IOException {
        String filePath = "src/test/resources/special_characters_output.csv";
        String data = "fruit,quantity\näpple,10\nbanäna,20";
        csvFileWriterService.writeToFile(data, filePath);
        assertTrue(Files.exists(Paths.get(filePath)));
    }
}
