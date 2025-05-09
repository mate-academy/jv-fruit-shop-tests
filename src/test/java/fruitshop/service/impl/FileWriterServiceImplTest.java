package fruitshop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String TEST_FILE = "src/test/resources/test_output.csv";
    private static final String INVALID_PATH_FILE = "/invalid_path/file.csv";
    private FileWriterService fileWriterService;

    @BeforeEach
    void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    void write_validData_fileWrittenOk() throws IOException {
        String data = "fruit,quantity\napple,100";
        fileWriterService.write(data, TEST_FILE);

        String actual = Files.readString(Path.of(TEST_FILE));
        assertEquals(data, actual);
    }

    @Test
    void write_invalidPath_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriterService.write("data", INVALID_PATH_FILE));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE));
    }
}
