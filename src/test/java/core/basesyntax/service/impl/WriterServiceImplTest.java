package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterServiceImplTest {
    private WriterServiceImpl writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeDataToFile_existingFile_ok() throws IOException {
        List<String> data = List.of("Line 1", "Line 2", "Line 3");
        Path filePath = Path.of("src/test/resources/testOutput.csv");
        writerService.writeDataToFile("src/test/resources/testOutput.csv", data);
        List<String> result = Files.readAllLines(filePath);
        assertEquals(data, result);
    }

    @Test
    void writeDataToFile_invalidPath_notOk() {
        List<String> data = List.of("Line 1", "Line 2", "Line 3");
        Assertions.assertThrows(RuntimeException.class,
                () -> writerService.writeDataToFile("test/src/test.csv", data));
    }
}
