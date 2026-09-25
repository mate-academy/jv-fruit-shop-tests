package core.basesyntax.filereader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileReaderImplTest {
    private static final String VALID_FILENAME = "reportToRead.csv";
    private static final String INVALID_FILENAME = "invalid.csv";
    private static FileReader fr;

    @BeforeAll
    static void beforeAll() {
        fr = new FileReaderImpl();
    }

    @Test
    void readFromFile_ValidFilename_Ok() {
        List<String> expected;
        try {
            expected = Files.readAllLines(Path.of(VALID_FILENAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't read the data from " + VALID_FILENAME, e);
        }

        List<String> actual = fr.readFromFile(VALID_FILENAME);

        assertEquals(expected, actual, "Execution returned wrong list: ");
    }

    @Test
    void readFromFile_InvalidFilename_NotOk() {
        assertThrows(RuntimeException.class, () -> fr.readFromFile(INVALID_FILENAME));
    }

    @Test
    void readFromFile_NullFilename_NotOk() {
        assertThrows(RuntimeException.class, () -> fr.readFromFile(null));
    }
}