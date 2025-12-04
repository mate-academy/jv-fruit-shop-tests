package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CsvReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvReaderImplTest {
    private static final String PATH = "src/test/resources/data.csv";
    private CsvReader csvReader;

    @BeforeEach
    void setUp() {
        csvReader = new CsvReaderImpl();
    }

    @Test
    void readAll_pathIsNull_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> csvReader.readAll(null));
    }

    @Test
    void readAll_canNotReadFile_notOk() {
        assertThrows(RuntimeException.class,
                () -> csvReader.readAll("src/test/resources/dataNotExisting.csv"));
    }

    @Test
    void readAll_validFile_isOk() {
        List<String> list = csvReader.readAll(PATH);
        assertEquals("    b,banana,20", list.get(1));
        assertEquals("    s,banana,50", list.get(8));
        assertEquals(9,list.size());
    }
}
