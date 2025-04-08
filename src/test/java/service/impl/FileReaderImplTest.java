package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FileReader;

class FileReaderImplTest {
    private static final String VALID_FILE = "src/test/java/resources/test.csv";
    private static final String EMPTY_FILE = "src/test/java/resources/empty.csv";
    private static final String INVALID_FILE = "src/test/java/resources/noSuchFile.csv";

    private FileReader reader;

    @BeforeEach
    void setUp() {
        reader = new FileReaderImpl();
    }

    @Test
    void readTestFile_ok() {
        List<String> lines = reader.read(VALID_FILE);

        assertEquals(11, lines.size());
        assertEquals("type,fruit,quantity", lines.get(0));
        assertEquals("b,apple,100", lines.get(1));
        assertEquals("s,banana,50", lines.get(2));
        assertEquals("s,orange,60", lines.get(3));
        assertEquals("r,grape,40", lines.get(4));
        assertEquals("b,banana,90", lines.get(5));
        assertEquals("p,orange,30", lines.get(6));
        assertEquals("p,grape,25", lines.get(7));
        assertEquals("r,apple,15", lines.get(8));
        assertEquals("s,apple,80", lines.get(9));
        assertEquals("p,banana,40", lines.get(10));
    }

    @Test
    void readEmptyFile_ok() {
        List<String> lines = reader.read(EMPTY_FILE);
        assertTrue(lines.isEmpty(), "Empty file expected");
    }

    @Test
    void readInvalidFile_notOk() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> reader.read(INVALID_FILE));
        assertEquals("File not found: " + INVALID_FILE, exception.getMessage());
    }
}
