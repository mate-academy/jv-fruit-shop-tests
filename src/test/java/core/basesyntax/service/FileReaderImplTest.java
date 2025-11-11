package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void read_validFile_ok() throws IOException {
        Path tempFile = Files.createTempFile("testInput", ".csv");
        Files.write(tempFile, List.of("line1", "line2"));
        List<String> result = fileReader.read(tempFile.toString());
        assertEquals(2, result.size());
        assertEquals("line1", result.get(0));
        assertEquals("line2", result.get(1));
    }

    @Test
    void read_fileNotExists_throws() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read("nonexistent_file_12345.csv"));
    }
}
