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
    private static final String EXPECTED_CONTENT = "fruit,quantity\nbanana,20";
    private static final String INVALID_PATH = "/invalid/path/test.csv";
    private static final String TEMP_FILE_PREFIX = "test_output";
    private static final String TEMP_FILE_SUFFIX = "csv";
    private CsvFileWriter csvFileWriter;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        csvFileWriter = new CsvFileWriterImpl();
        tempFile = Files.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX);
    }

    @Test
    void write_validContent_writesToFile() throws IOException {
        csvFileWriter.write(EXPECTED_CONTENT, tempFile.toString());
        String actualContent = Files.readString(tempFile);
        assertEquals(EXPECTED_CONTENT, actualContent,
                "File content should match written content");
    }

    @Test
    void write_invalidPath_throwsRuntimeException() {
        assertThrows(RuntimeException.class,
                () -> csvFileWriter.write(EXPECTED_CONTENT, INVALID_PATH),
                "Should throw RuntimeException for invalid path");
    }

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(tempFile);
    }
}
