package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String VALID_FILE = "src/test/java/resources/test.csv";
    private static final String EMPTY_FILE = "src/test/java/resources/empty.csv";
    private static final String NON_EXISTENT_FILE = "src/test/java/resources/nonExistentFile.csv";

    private FileReaderImpl fileReader;

    @Before
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readValidFile_ok() {
        List<String> lines = fileReader.read(VALID_FILE.toString());

        assertEquals(9, lines.size());
        assertEquals("type,fruit,quantity", lines.get(0));
        assertEquals("b,banana,20", lines.get(1));
        assertEquals("b,apple,100", lines.get(2));
        assertEquals("s,banana,100", lines.get(3));
        assertEquals("p,banana,13", lines.get(4));
        assertEquals("r,apple,10", lines.get(5));
        assertEquals("p,apple,20", lines.get(6));
        assertEquals("p,banana,5", lines.get(7));
        assertEquals("s,banana,50", lines.get(8));
    }

    @Test
    public void readInvalidFile_notOk() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> fileReader.read(NON_EXISTENT_FILE));
        assertEquals("File not found: " + NON_EXISTENT_FILE, exception.getMessage());
    }

    @Test
    public void readEmptyFile_ok() {
        List<String> lines = fileReader.read(EMPTY_FILE);
        assertTrue(lines.isEmpty(), "Expected the file to be empty");
    }
}
