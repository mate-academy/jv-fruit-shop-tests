package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.models.InvalidDataException;
import core.basesyntax.service.DataReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvDataReaderImplTest {
    private static final String EMPTY_FILE = "src/main/resources/test_EmptyFile.csv";
    private static final String EMPTY_PATH = "";
    private static final String NULL_PATH = null;
    private static final String VALID_FILE = "src/main/resources/valid_data.csv";

    private static DataReader dataReader;

    @BeforeAll
    static void beforeAll() {
        dataReader = new CsvDataReaderImpl();
    }

    @Test
    void getDataFromFile_validFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        List<String> result = dataReader.getDataFromFile(VALID_FILE);
        assertEquals(expected, result);
    }

    @Test
    void getDataFromFile_emptyPath_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            dataReader.getDataFromFile(EMPTY_PATH);
        });
    }

    @Test
    void getDataFromFile_nullValue_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            dataReader.getDataFromFile(NULL_PATH);
        });
    }

    @Test
    void getDataFromFile_emptyFile_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = dataReader.getDataFromFile(EMPTY_FILE);
        assertEquals(expected, actual);
    }
}
