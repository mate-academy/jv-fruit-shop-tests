package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private final FileReaderImpl fileReader = new FileReaderImpl();

    @Test
    void read_ok() throws Exception {
        Path file = Files.createTempFile("test", ".cvs");
        Files.write(file, List.of("a", "b"));

        List<String> result = fileReader.read(file.toString());

        assertEquals(List.of("a","b"), result);
    }

    @Test
    void read_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read("unknown-file.csv"));
    }
}
