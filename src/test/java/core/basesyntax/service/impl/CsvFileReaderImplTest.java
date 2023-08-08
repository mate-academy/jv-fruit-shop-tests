package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CsvFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderImplTest {
    private static final String VALID_FILE_PATH = "src/test/resources/validData.csv";
    private static final String INVALID_FILE_PATH = "src/test/resources/validdaata.csv";
    private static final String NULL_FILE_PATH = null;
    private static final String CORRECT_CONTENT = "type,fruit,quantity" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,100" + System.lineSeparator()
            + "p,banana,13" + System.lineSeparator()
            + "r,apple,10";
    private CsvFileReader csvFileReader;

    @BeforeEach
    void setUp() {
        csvFileReader = new CsvFileReaderImpl();
    }

    @Test
    void read_validData_ok() {
        String content = csvFileReader.read(VALID_FILE_PATH);
        assertEquals(CORRECT_CONTENT, content);
    }

    @Test
    void read_InvalidFilePath_notOk() {
        assertThrows(RuntimeException.class,
                () -> csvFileReader.read(INVALID_FILE_PATH));
    }

    @Test
    void read_nullFilePath_notOk() {
        assertThrows(RuntimeException.class,
                () -> csvFileReader.read(NULL_FILE_PATH));
    }
}
