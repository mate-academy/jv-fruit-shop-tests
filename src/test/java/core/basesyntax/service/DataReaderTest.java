package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.impl.DataReaderCsvImpl;
import java.util.List;
import org.junit.Test;

public class DataReaderTest {
    private static final String DATA_PATH_OK = "src/test/resources/FruitShopOk.csv";
    private static final String INCORRECT_PATH = "src/test/resssources/Fruit.csv";

    @Test
    public void readTest_Ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "b,orange,150",
                "s,banana,100",
                "s,orange,20",
                "p,banana,13");
        DataReader dataReader = new DataReaderCsvImpl();
        List<String> actual = dataReader.read(DATA_PATH_OK);
        assertEquals(expected, actual);
    }

    @Test
    public void incorrectPathTest_NotOk() {
        DataReader dataReader = new DataReaderCsvImpl();
        try {
            dataReader.read(INCORRECT_PATH);
        } catch (RuntimeException e) {
            return;
        }
        fail("DataReader should be thrown: \"Can't read data from file: filePath\"");
    }
}
