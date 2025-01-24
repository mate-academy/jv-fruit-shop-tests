package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CustomFileReaderTest {
    private static final String REPORT_TO_READ = "src/main/resources/reportToRead.csv";
    private static final String EMPTY_REPORT = "src/main/resources/emptyReport.csv";
    private static CustomFileReader customFileReader;

    @BeforeAll
    static void beforeAll() {
        customFileReader = new CustomFileReaderImpl();
    }

    @Test
    void readOk() {
        List<String> read = customFileReader.read(REPORT_TO_READ);
        assertNotNull(read);
        assertFalse(read.isEmpty());
    }

    @Test
    void readNotOk() {
        assertThrows(RuntimeException.class, () -> customFileReader.read("randomFile"));
        List<String> read = customFileReader.read(EMPTY_REPORT);
        assertTrue(read.isEmpty());
        assertEquals(0, read.size());
    }

    @Test
    void readFileWithSpecialCharactersIsOk() {
        List<String> read = customFileReader.read("src/main/resources/reportWithSpecialChars.csv");
        assertNotNull(read);
        assertFalse(read.isEmpty());
    }
}
