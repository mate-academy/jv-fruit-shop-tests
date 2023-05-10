package core.basesyntax.service.impl;

import core.basesyntax.service.WriteScvService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class WriteScvServiceImlTest {
    private static final String DATA = "fruit,quantity\nbanana,50\napple,25\n";
    private WriteScvService writeScvService;

    @BeforeEach
    void setUp() {
        writeScvService = new WriteScvServiceIml();
    }

    @Test
    void writeDataScvFile_ok() throws IOException {
        Path tempFile = Files.createTempFile("testFile", ".csv");
        String filePath = tempFile.toString();
        writeScvService.writeDataScvFile(DATA, filePath);
        String fileContent = Files.readString(tempFile);
        assertEquals(fileContent, DATA);
        Files.delete(tempFile);
    }

    @Test
    void writeDataScvFile_notOk() {
        String filePath = "/non/existent/directory/testFile.csv";
        assertThrows(RuntimeException.class,
                () -> writeScvService.writeDataScvFile(DATA, filePath));
    }
}
