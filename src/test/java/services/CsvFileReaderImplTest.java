package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.impl.CsvReaderImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderImplTest {
    private static final String VALID_PATH = "src/test/resources/ReadTestFile.csv";
    private static final String INVALID_PATH = "";
    private static final List<String> FILE_CONTENT = List.of("type,fruit,quantity",
            "    b,banana,20",
            "    b,apple,100",
            "    s,banana,100");

    private CsvReaderImpl csvReader;

    @BeforeEach
    void setUp() {
        csvReader = new CsvReaderImpl();
    }

    @Test
    void testRead_Ok() {
        List<String> result = csvReader.readFile(VALID_PATH);
        assertEquals(FILE_CONTENT, result);
    }

    @Test
    void testRead_invalidPath_notOk() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> csvReader.readFile(INVALID_PATH));
        assertEquals("Can't read file ", runtimeException.getMessage());
    }

}
