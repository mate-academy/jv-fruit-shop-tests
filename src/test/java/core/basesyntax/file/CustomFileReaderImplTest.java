package core.basesyntax.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    private static final String INVALID_FILE_PATH = "src/main/resources/fruitShop.csv";
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

        assertEquals(3, lines.size(), "Failed in read_validFile_success: "
                + "expected 3 lines");
        assertEquals("Line 1", lines.get(0), "Failed in read_validFile_success: "
                + "First line incorrect");
        assertEquals("Line 2", lines.get(1), "Failed in read_validFile_success: "
                + "Second line incorrect");
        assertEquals("Line 3", lines.get(2), "Failed in read_validFile_success: "
                + "Third line incorrect");

        Files.deleteIfExists(new File(TEST_FILE_PATH).toPath());
    }

    @Test
    void readingInvalidFilePath_throwsRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                fileReader.read(INVALID_FILE_PATH));
        assertEquals("Can't read file at " + INVALID_FILE_PATH, exception.getMessage());
    }
}
