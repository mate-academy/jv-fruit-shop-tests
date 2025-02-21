package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.impl.CsvReaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderImplTest {
    private static final String VALID_PATH = "src/test/java/resources/ReadTestFile.csv";
    private static final String Invalid_Path = "";
    private static final String FILE_CONTENT = "Test file";
    private CsvReaderImpl csvReader;

    @BeforeEach
    void setUp() {
        csvReader = new CsvReaderImpl();
    }

    @Test
    void testRead_Ok() {
        String result = csvReader.readFile(VALID_PATH);
        assertEquals(FILE_CONTENT, result);
    }

    @Test
    void testRead_invalidPath() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> csvReader.readFile(Invalid_Path));
        assertEquals("Can't read file", runtimeException.getMessage());
    }
}
