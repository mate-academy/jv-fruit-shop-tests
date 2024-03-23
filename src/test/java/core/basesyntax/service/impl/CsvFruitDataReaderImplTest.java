package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.CantReadFromFileException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFruitDataReaderImplTest {
    private static final String CORRECT_FILE_PATH = "src\\resources\\file.csv";
    private static final String INCORRECT_FILE_PATH = "src\\resources\\incorrect_path.csv";
    private static final String EMPTY_FILE_PATH = "src\\resources\\emptyfile.csv";
    private static final List<String> DATA_LIST = new ArrayList<>();
    private static final List<String> EMPTY_DATA_LIST = new ArrayList<>();
    private static CsvFruitDataReaderImpl dataReader;

    @BeforeAll
    static void setUp() {
        DATA_LIST.add("type,fruit,quantity");
        DATA_LIST.add("b,banana,20");
        DATA_LIST.add("b,apple,100");
        DATA_LIST.add("s,banana,100");
        DATA_LIST.add("p,banana,13");
        DATA_LIST.add("r,apple,10");
        DATA_LIST.add("p,apple,20");
        DATA_LIST.add("p,banana,5");
        DATA_LIST.add("s,banana,50");
    }

    @BeforeEach
    void setUpReader() {
        dataReader = new CsvFruitDataReaderImpl();
    }

    @Test
    void readData_FileExists_Ok() {
        List<String> dataFromMethod = dataReader.readData(CORRECT_FILE_PATH);
        assertEquals(DATA_LIST, dataFromMethod);
    }

    @Test
    void readDataNonExistentFile_NotOk() {
        assertThrows(CantReadFromFileException.class,
                () -> dataReader.readData(INCORRECT_FILE_PATH));
    }

    @Test
    void readDataFromEmptyFile_Ok() {
        List<String> dataFromMethod = dataReader.readData(EMPTY_FILE_PATH);
        assertEquals(EMPTY_DATA_LIST, dataFromMethod);
    }

    @Test
    void readDataWithNullParam_NotOk() {
        assertThrows(CantReadFromFileException.class,
                () -> dataReader.readData(null));
    }

}
