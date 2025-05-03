package core.basesyntax.filewriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String TEST_OUTPUT_FILE = "src/test/resources/output.csv";
    private FileWriterImpl fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(Path.of(TEST_OUTPUT_FILE));
    }

    @Test
    void write_validInput_fileIsCreatedWithContent() throws IOException {
        String content = "banana,20\napple,30";
        fileWriter.write(content, TEST_OUTPUT_FILE);

        List<String> lines = Files.readAllLines(Path.of(TEST_OUTPUT_FILE));
        assertEquals(2, lines.size());
        assertEquals("banana,20", lines.get(0));
        assertEquals("apple,30", lines.get(1));
    }

    @Test
    void write_toInvalidPath_throwsException() {
        String invalidPath = "src/test/resources/invalid_dir/output.csv";
        String content = "test";

        Exception exception = assertThrows(RuntimeException.class, () ->
                fileWriter.write(content, invalidPath)
        );
        assertTrue(exception.getMessage().contains("Cant read file by path"));
    }

    @Test
    void write_emptyContent_createsEmptyFile() throws IOException {
        fileWriter.write("", TEST_OUTPUT_FILE);
        String fileContent = Files.readString(Path.of(TEST_OUTPUT_FILE));
        assertEquals("", fileContent);
    }
}
