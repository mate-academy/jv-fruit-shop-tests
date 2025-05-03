package core.basesyntax.filewriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileWriterImplTest {
    private static final String EXPECTED_CONTENT_FILE = "src/test/resources/expected_fruit.csv";
    private static final String INVALID_PATH = "/invalid/path/test.csv";
    private static final String TEST_OUTPUT_FILE = "src/test/resources/test_output.csv";
    private CsvFileWriter csvFileWriter;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        csvFileWriter = new CsvFileWriterImpl();
        tempFile = Path.of(TEST_OUTPUT_FILE);
        if (!Files.exists(tempFile)) {
            Files.createFile(tempFile);
        }
    }

    @Test
    void write_validContent_writesToFile() throws IOException {
        String expectedContent = Files.readString(Path.of(EXPECTED_CONTENT_FILE));
        csvFileWriter.write(expectedContent, tempFile.toString());
        String actualContent = Files.readString(tempFile);
        assertEquals(expectedContent, actualContent,
                "File content should match written content");
    }

    @Test
    void write_invalidPath_throwsRuntimeException() throws IOException {
        String expectedContent = Files.readString(Path.of(EXPECTED_CONTENT_FILE));
        assertThrows(RuntimeException.class,
                () -> csvFileWriter.write(expectedContent, INVALID_PATH),
                "Should throw RuntimeException for invalid path");
    }

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(tempFile);
    }
}
