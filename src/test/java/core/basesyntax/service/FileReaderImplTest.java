package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {

    private final FileReader fileReader = new FileReaderImpl();

    @Test
    void read_validFile_ok() throws IOException {
        String filePath = "src/test/resources/test.csv";
        Files.write(Paths.get(filePath), List.of("type,fruit,quantity", "b,apple,100"));

        List<String> result = fileReader.read(filePath);

        assertEquals(2, result.size());
        assertEquals("b,apple,100", result.get(1));

        Files.delete(Paths.get(filePath));
    }

    @Test
    void read_nonExistentFile_throwsException() {
        String filePath = "src/test/resources/nonexistent.csv";
        Exception exception = assertThrows(RuntimeException.class, () -> fileReader.read(filePath));
        assertTrue(exception.getMessage().contains("Can't read file"));
    }
}
