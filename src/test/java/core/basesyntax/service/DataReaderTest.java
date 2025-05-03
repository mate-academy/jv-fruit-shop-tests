package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.DataReaderCsvImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataReaderTest {
    private static final String DATA_PATH_OK = "src/test/resources/FruitShopOk.csv";
    private static final String INCORRECT_PATH = "src/test/resssources/Fruit.csv";
    private static List<String> correctData;
    private static DataReader dataReader;

    @BeforeClass
    public static void setUp() {
        dataReader = new DataReaderCsvImpl();
        correctData = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "b,orange,150",
                "s,banana,100",
                "s,orange,20",
                "p,banana,13");
    }

    @Test
    public void read_correctDataTest_Ok() {
        List<String> expected = correctData;
        List<String> actual = dataReader.read(DATA_PATH_OK);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_incorrectPathTest_notOk() {
        dataReader.read(INCORRECT_PATH);
    }
}
