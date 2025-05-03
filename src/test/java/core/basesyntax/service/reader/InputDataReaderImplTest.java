package core.basesyntax.service.reader;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class InputDataReaderImplTest {
    private static final String READER_TEST_FILE_PATH = "src/test/resources/reader_test.csv";
    private static final String WRONG_FILE_PATH = "src/test/resource/reader_test.csv";
    private static List<String> expected;
    private static InputDataReader inputDataReader;

    @BeforeClass
    public static void beforeClass() {
        inputDataReader = new InputDataReaderImpl();
        expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
    }

    @Test
    public void getDataFromFile_ok() {
        List<String> actual = inputDataReader.getDataFromFile(READER_TEST_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getDataFromFile_wrongPath_notOk() {
        inputDataReader.getDataFromFile(WRONG_FILE_PATH);
    }
}
