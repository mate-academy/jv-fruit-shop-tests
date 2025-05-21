package basesyntax.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReaderInterface fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validFiles_Ok() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        Files.write(tempFile, List.of("line1", "line2", "line3"));

        List<String> result = fileReader.read(tempFile.toString());

        assertEquals(List.of("line1", "line2", "line3"), result);
        Files.deleteIfExists(tempFile);
    }

    @Test
    void read_nullPath_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read(null));
    }

    @Test
    void read_emptyFile_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReader.read(""));
    }

    @Test
    void read_nonExistentFile_NotOk() {
        String fakePath = "non-existent-file.csv";
        assertThrows(RuntimeException.class, () -> fileReader.read(fakePath));
    }
}
