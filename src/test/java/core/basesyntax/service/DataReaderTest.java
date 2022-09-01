package core.basesyntax.service;

import core.basesyntax.service.impl.DataReaderImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataReaderTest {
    private static final String APPLES_ORANGES = "src/test/resources/applesOranges.csv";
    private DataReader dataReader;

    @Before
    public void setUp() {
        dataReader = new DataReaderImpl();
    }

    @Test
    public void readData_Ok() {
        List<String> dataList = dataReader.readData(APPLES_ORANGES);
        String actualResult = dataList.stream().collect(Collectors.joining(System.lineSeparator()));
        String expectedResult = "b,apple,35" + System.lineSeparator()
                + "b,orange,42" + System.lineSeparator()
                + "s,apple,70" + System.lineSeparator()
                + "p,apple,15" + System.lineSeparator()
                + "r,orange,22";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test (expected = RuntimeException.class)
    public void readDataFromWrongFileName_notOk() {
        List<String> dataList = dataReader.readData("wrongFileName.csv");
    }

    @Test (expected = RuntimeException.class)
    public void readDataNull_notOk() {
        List<String> dataList = dataReader.readData(null);
    }

}
