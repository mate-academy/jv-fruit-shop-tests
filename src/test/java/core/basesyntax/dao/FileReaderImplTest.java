package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderImplTest {
    private FileReaderImpl fileReader;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_nullInput_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read(null));
    }

    @Test
    void read_emptyInput_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read(""));
    }

    @Test
    void read_validFile_ok() throws IOException {
        Path file = tempDir.resolve("test.csv");
        Files.writeString(file, "p,banana,10");
        List<String> actual = fileReader.read(file.toString());
        List<String> expected = List.of("p,banana,10");
        assertEquals(expected, actual);
    }

}
