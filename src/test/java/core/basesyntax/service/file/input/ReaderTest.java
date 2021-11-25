package core.basesyntax.service.file.input;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderTest {
    private static Reader reader;
    private static final String FIRST_OK_PATH = "src/test/resources/InputDataIsOkFirst.csv";
    private static final String SECOND_OK_PATH = "src/test/resources/InputDataIsOkSecond.csv";
    private static final String EMPTY_INPUT_DATA_PATH = "src/test/resources/emptyInputData.csv";
    private static final String INVALID_INPUT_DATA_PATH = "src/java/resources/blaBlaBla.csv";

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderImpl();
    }

    @Test
    public void readFromFile_InputDataIsOkFirst_Ok() {
        List<String> expected =
                List.of("type,fruit,quantity",
                        "b,banana,100",
                        "b,apple,110",
                        "s,banana,60",
                        "p,banana,5",
                        "p,banana,4",
                        "p,banana,6",
                        "r,apple,10",
                        "p,apple,20",
                        "p,apple,12",
                        "p,apple,5",
                        "p,banana,5",
                        "s,banana,50");
        List<String> actual = reader.read(FIRST_OK_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_InputDataIsOkSecond_Ok() {
        List<String> expected =
                List.of("type,fruit,quantity",
                        "p,apple,-20",
                        "1231616454654f36asf6");
        List<String> actual = reader.read(SECOND_OK_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_emptyInputData_Ok() {
        List<String> dataFromFile = reader.read(EMPTY_INPUT_DATA_PATH);
        int actual = dataFromFile.size();
        int expected = 0;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullPath_NotOk() {
        reader.read(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_invalidInputDataPath_NotOk() {
        reader.read(INVALID_INPUT_DATA_PATH);
    }
}
