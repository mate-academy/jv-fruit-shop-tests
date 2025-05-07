package fruitshop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.service.FileWriterService;
import fruitshop.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {
    private static final String TEST_FILE = "src/test/resources/test_output.csv";
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
    void write_nullData_notOk() {
        assertThrows(NullPointerException.class, () -> fileWriterService.write(null, TEST_FILE));
    }

    @Test
    void write_nullFilePath_notOk() {
        assertThrows(NullPointerException.class, () -> fileWriterService.write("some data", null));
    }

    @Test
    void write_invalidPath_notOk() {
        String invalidPath = "/invalid_path/file.csv";
        assertThrows(RuntimeException.class, () ->
                fileWriterService.write("data", invalidPath));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE));
    }
}
