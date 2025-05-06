package core.basesyntax.report.output;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_OUTPUT_FILE = "src/test/resources/test_output.csv";
    private FileWriterImpl fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_OUTPUT_FILE));
    }

    @Test
    void write_validContent_fileCreatedAndContentMatches() throws IOException {
        String content = "fruit,quantity\napple,10\nbanana,20";
        fileWriter.write(content, TEST_OUTPUT_FILE);
        String writtenContent = Files.readString(Paths.get(TEST_OUTPUT_FILE));
        assertEquals(content, writtenContent);
    }

    @Test
    void write_invalidPath_throwsRuntimeException() {
        String invalidPath = "/invalid_path/test_output.csv";
        String content = "fruit,quantity\napple,10";

        Exception exception = assertThrows(RuntimeException.class, () ->
                fileWriter.write(content, invalidPath)
        );

        assertTrue(exception.getCause() instanceof IOException);
    }

}
