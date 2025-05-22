package basesyntax.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/validFile.csv";
    private static FileReaderInterface fileReader;

    @BeforeAll
    static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_validFiles_Ok() {
        List<String> result = fileReader.read(VALID_FILE_PATH);
        assertEquals(List.of("line1", "line2", "line3"), result);
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
