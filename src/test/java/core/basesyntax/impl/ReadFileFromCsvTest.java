package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.service.ReadFileService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReadFileFromCsvTest {
    private static final String VALID_FILE_PATH = "src/test/resources/test_input.csv";
    private static final String INVALID_FILE_TO_READ = "invalid_file.csv";
    private static ReadFileService fileReader;

    @BeforeAll
    static void setUp() {
        fileReader = new ReadFileFromCsv();
    }

    @Test
    public void read_ValidFile_Ok() {
        List<String> actual = fileReader.readFromFile(VALID_FILE_PATH);
        List<String> expected = List.of("Input", "To", "Read");
        assertNotNull(actual);
        assertEquals(3, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void read_NonexistentFile_ExceptionThrown() {
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class, () -> fileReader.readFromFile(INVALID_FILE_TO_READ));
        assertEquals(runtimeException.getMessage(),
                "Can't read from file with path: invalid_file.csv");
    }
}
