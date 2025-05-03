package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderCsvImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderTest {
    private static Reader reader;
    private static final String VALID_INPUT_DATA_PATH = "src/test/resources/validInputData.csv";
    private static final String EMPTY_INPUT_DATA_PATH = "src/test/resources/emptyInputData.csv";
    private static final String INVALID_INPUT_DATA_PATH = "src/java/resources/emptyInputData.csv";

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderCsvImpl();
    }

    @Test
    public void readFromFile_ValidData_ok() {
        List<String> expected =
                List.of("type,fruit,quantity",
                        "b,banana,20",
                        "b,apple,100",
                        "s,banana,100",
                        "p,banana,13",
                        "r,apple,10",
                        "p,apple,20",
                        "p,banana,5",
                        "s,banana,50");
        List<String> actual = reader.readFromFile(VALID_INPUT_DATA_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_emptyData_ok() {
        int expected = 0;
        List<String> dataFromFile = reader.readFromFile(EMPTY_INPUT_DATA_PATH);
        int actual = dataFromFile.size();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullPath_NotOk() {
        reader.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_invalidInputDataPath_NotOk() {
        reader.readFromFile(INVALID_INPUT_DATA_PATH);
    }
}
