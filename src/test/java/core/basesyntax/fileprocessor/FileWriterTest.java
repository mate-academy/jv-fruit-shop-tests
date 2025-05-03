package core.basesyntax.fileprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterTest {

    private static final String VALID_FILE_PATH = "test_output_file.csv";
    private static final String UNWRITABLE_FILE_PATH = "unwritable_file.csv";
    private FileWriter fileWriter;

    @BeforeEach
    void setUp() throws IOException {
        fileWriter = new FileWriter();
        Path unwritableFilePath = Path.of(UNWRITABLE_FILE_PATH);
        Path testFilePath = Path.of(VALID_FILE_PATH);

        Files.deleteIfExists(testFilePath);
        Files.deleteIfExists(unwritableFilePath);

        Files.createFile(testFilePath);
        testFilePath.toFile().setWritable(true);

        Files.createFile(unwritableFilePath);
        unwritableFilePath.toFile().setWritable(false);
    }

    @AfterEach
    void tearDown() throws IOException {
        Path unwritableFilePath = Path.of(UNWRITABLE_FILE_PATH);
        Path testFilePath = Path.of(VALID_FILE_PATH);
        if (Files.exists(unwritableFilePath)) {
            unwritableFilePath.toFile().setWritable(true);
            Files.delete(unwritableFilePath);
        }
        if (Files.exists(testFilePath)) {
            Files.delete(testFilePath);
        }
    }

    @Test
    void write_validData_createsFileWithContent() throws IOException {
        List<String> data = List.of("line1", "line2", "line3");
        fileWriter.write(data, VALID_FILE_PATH);
        List<String> result = Files.readAllLines(Path.of(VALID_FILE_PATH));
        assertEquals(data, result);
    }

    @Test
    void write_unwritableFile_throwsRuntimeException() {
        List<String> data = List.of("line1", "line2");
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> fileWriter.write(data, UNWRITABLE_FILE_PATH)
        );
        assertTrue(exception.getMessage().startsWith("Failed to write data to file: "));
    }

    @Test
    void write_emptyData_createsEmptyFile() throws IOException {
        List<String> data = List.of();
        fileWriter.write(data, VALID_FILE_PATH);
        List<String> result = Files.readAllLines(Path.of(VALID_FILE_PATH));
        assertEquals(0, result.size());
    }
}
