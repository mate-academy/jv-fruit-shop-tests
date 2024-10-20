package core.basesyntax.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomFileReaderImplTest {
    private static final String TEST_FILE_PATH = "test_file.txt";
    private CustomFileReaderImpl fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new CustomFileReaderImpl();
    }

    @Test
    void read_validFile_success() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH))) {
            writer.write("Line 1\n");
            writer.write("Line 2\n");
            writer.write("Line 3\n");
        }

        List<String> lines = fileReader.read(TEST_FILE_PATH);

        assertEquals(3, lines.size(), "Can be 3 lines");
        assertEquals("Line 1", lines.get(0), "First line incorrect");
        assertEquals("Line 2", lines.get(1), "Second line incorrect");
        assertEquals("Line 3", lines.get(2), "Third line incorrect");

        // Удаляем тестовый файл
        Files.deleteIfExists(new File(TEST_FILE_PATH).toPath());
    }

    @Test
    void read_fileDoesNotExist_throwsRuntimeException() {
        String nonExistentFile = "non_existent_file.txt";

        Exception exception = assertThrows(RuntimeException.class,
                () -> fileReader.read(nonExistentFile));

        assertTrue(exception.getMessage().contains("Can't read file"));
    }
}
