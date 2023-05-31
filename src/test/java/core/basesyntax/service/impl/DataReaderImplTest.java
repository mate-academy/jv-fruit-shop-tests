package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.DataReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class DataReaderImplTest {
    private static final String NON_EXISTENT_FILE = "src/main/data.csv";
    private static final String EMPTY_FILE_PATH = "src/main/resources/emptyFile.csv";
    private static final String NORMAL_FILE_PATH = "src/main/resources/data.csv";
    private final DataReader dataReader = new DataReaderImpl();

    @Test(expected = RuntimeException.class)
    public void readData_nonExistentFile_notOk() {
        dataReader.readData(NON_EXISTENT_FILE);
    }

    @Test
    public void readData_emptyFile_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = dataReader.readData(EMPTY_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void readData_normalFile_ok() {
        List<String> expected = List.of("type,fruit,quantity","b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual = dataReader.readData(NORMAL_FILE_PATH);
        assertEquals(expected, actual);
    }
}
