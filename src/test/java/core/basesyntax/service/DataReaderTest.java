package core.basesyntax.service;

import core.basesyntax.service.impl.DataReaderImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataReaderTest {
    private static final String CORRECT_INPUT_FILE = "src/test/resources/correctData.csv";
    private DataReader dataReader;

    @Before
    public void setUp() {
        dataReader = new DataReaderImpl();
    }

    @Test
    public void readData_Ok() {
        List<String> dataList = dataReader.readData(CORRECT_INPUT_FILE);
        String actualResult = dataList.stream()
                .collect(Collectors.joining(System.lineSeparator()));
        List<String> expectdList = List.of("b,apple,35", "b,orange,42", "s,apple,70",
                "p,apple,15", "r,orange,22");
        String expectedResult = expectdList.stream()
                .collect(Collectors.joining(System.lineSeparator()));
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test (expected = RuntimeException.class)
    public void readData_nonExistentFile_notOk() {
        List<String> dataList = dataReader.readData("wrongFileName.csv");
    }

    @Test (expected = RuntimeException.class)
    public void readData_nullFile_notOk() {
        List<String> dataList = dataReader.readData(null);
    }
}
