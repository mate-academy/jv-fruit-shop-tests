package core.basesyntax.service.implemantation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderImplTest {
    private static final String CORRECT_PATH_FILE = "src/main/resources/input.csv";
    private static final String INVALID_PATH_FILE = "src/main/resources/inputBad.csv";
    private static final int COUNT_OF_LINES = 9;
    private static final String EXCEPTION_MESSAGE_INVALID_PATH =
            "Can't read data from file " + INVALID_PATH_FILE;
    private FileReader fileReader;

    @BeforeEach
    void setUp() {
        fileReader = new CsvFileReaderImpl();
    }

    @Test
    void read_CorrectPathFile_OK() {
        List<String> lines = fileReader.readData(CORRECT_PATH_FILE);
        assertNotNull(lines);
        assertEquals(COUNT_OF_LINES, lines.size());
    }

    @Test
    void read_InvalidPathFile() {
        var readFailure = assertThrows(RuntimeException.class,
                () -> fileReader.readData(INVALID_PATH_FILE));
        assertEquals(EXCEPTION_MESSAGE_INVALID_PATH, readFailure.getMessage());
    }
}
