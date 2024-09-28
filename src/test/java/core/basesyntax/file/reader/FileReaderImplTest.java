package core.basesyntax.file.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validFile_ok() throws IOException {
        Path tempFile = Files.createTempFile("test", ".csv");
        Files.write(tempFile, List.of("type,fruit,quantity", "b,banana,20"));

        List<String> result = fileReader.read(tempFile.toString());

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("type,fruit,quantity", result.get(0));
        assertEquals("b,banana,20", result.get(1));

        Files.delete(tempFile);
    }

    @Test
    void read_fileNotFound_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read("non_existing_file.csv"));
    }
}
