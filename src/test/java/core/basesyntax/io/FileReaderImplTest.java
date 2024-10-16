package core.basesyntax.io;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String EXISTING_FILE_PATH = "src/test/resources/testInput.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";
    private static final String INVALID_FORMAT_FILE_PATH
            = "src/test/resources/invalidFormatFile.csv";
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_existingFile_ok() {
        List<String> lines = fileReader.read(EXISTING_FILE_PATH);
        assertEquals(6, lines.size());
    }

    @Test
    public void shouldReturnEmptyListWhenFileIsEmpty() throws IOException {
        Files.createFile(Path.of(EMPTY_FILE_PATH));

        List<String> lines = fileReader.read(EMPTY_FILE_PATH);
        assertTrue(lines.isEmpty(), "Expected empty list for empty file");

        // Удаляем файл после теста
        Files.delete(Path.of(EMPTY_FILE_PATH));
    }

    @Test
    public void shouldReadAllLinesWhenFileHasInvalidFormat() throws IOException {
        Files.write(Path.of(INVALID_FORMAT_FILE_PATH), List.of(
                "This is not CSV", "Neither is this"));

        List<String> lines = fileReader.read(INVALID_FORMAT_FILE_PATH);
        assertEquals(2, lines.size());
        assertEquals("This is not CSV", lines.get(0));

        Files.delete(Path.of(INVALID_FORMAT_FILE_PATH));
    }
}
