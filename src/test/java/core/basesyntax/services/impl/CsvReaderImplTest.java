package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvReaderImplTest {
    private static final String VALID_PATH = "src/test/resources/testRead.csv";
    private static final String INVALID_PATH = "";
    private static final String FILE_CONTENT = "This is a file for test.";
    private CsvReaderImpl csvReader;

    @BeforeEach
    void setUp() {
        csvReader = new CsvReaderImpl();
    }

    @Test
    void read_validPath_ok() {
        String result = csvReader.read(VALID_PATH);
        assertEquals(FILE_CONTENT, result);
    }

    @Test
    void read_invalidPath_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> csvReader.read(INVALID_PATH));
        assertEquals("Can't read file ", runtimeException.getMessage());
    }
}
