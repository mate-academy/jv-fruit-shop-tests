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
    private static final String PATH_TO_TEST_OUTPUT_CSV_FILE = "src/test/resources/testOutput.csv";
    private static final String INVALID_PATH = "/non_existing_path.txt";
    private WriterServiceImpl writerService;

    @BeforeEach
    void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    void writeDataToFile_existingFile_ok() throws IOException {
        List<String> data = List.of("Line 1", "Line 2", "Line 3");
        String filePath = PATH_TO_TEST_OUTPUT_CSV_FILE;
        writerService.writeDataToFile(filePath, data);
        List<String> result = Files.readAllLines(Path.of(filePath));
        assertEquals(data, result);
    }

    @Test
    void writeDataToFile_invalidPath_notOk() {
        List<String> data = List.of("Line 1", "Line 2", "Line 3");
        Assertions.assertThrows(RuntimeException.class,
                () -> writerService.writeDataToFile(INVALID_PATH, data));
    }
}
