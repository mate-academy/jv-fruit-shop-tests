package core.basesyntax.service;

import core.basesyntax.errors.DataReaderError;
import core.basesyntax.service.impl.DataReaderCsv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataReaderTest {
    private static DataReader dataReader;

    @BeforeAll
    static void beforeAll() {
        dataReader = new DataReaderCsv();
    }

    @Test
    void read_nullFileName_notOk() {
        Assertions.assertThrows(DataReaderError.class,
                () -> dataReader.read(null));
    }

    @Test
    void read_emptyFileName_notOk() {
        Assertions.assertThrows(DataReaderError.class,
                () -> dataReader.read(""));
    }

    @Test
    void read_invalidFileName_notOk() {
        // Folder
        Assertions.assertThrows(DataReaderError.class,
                () -> dataReader.read("src/test/resources/"));
    }

    @Test
    void read_missingFile_notOk() {
        Assertions.assertThrows(DataReaderError.class,
                () -> dataReader.read("src/test/resources/data1.csv"));
    }

    @Test
    void read_emptyFile_notOk() {
        Assertions.assertThrows(DataReaderError.class,
                () -> dataReader.read("src/test/resources/data_empty.csv"));
    }

    @Test
    void read_existingFile_ok() {
        // Only header without data
        String[] data = dataReader.read("src/test/resources/data_only_header.csv");
        Assertions.assertEquals(1, data.length,
                "Read " + data.length + " lines, but expect 1");
        // Normal file
        data = dataReader.read("src/test/resources/data.csv");
        Assertions.assertEquals(9, data.length,
                "Read " + data.length + " lines, but expect 9");
        // Cyrillic file name
        data = dataReader.read("src/test/resources/магазин.csv");
        Assertions.assertEquals(9, data.length,
                "Read " + data.length + " lines, but expect 9");
    }
}
