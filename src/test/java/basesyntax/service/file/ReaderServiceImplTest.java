package basesyntax.service.file;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    public static final String FILE_WITH_DATA_PATH = "src/test/resources/test.csv";
    public static final String EMPTY_FILE_PATH = "src/test/resources/empty.csv";
    public static final String COLUMN_NAMES = "operation,fruit,amount";
    public static final String INCORRECT_PATH = "wrong path";
    public static final String DATA_FROM_FILE_FIRST = "b,banana,15";
    public static final String DATA_FROM_FILE_SECOND = "b,apple,10";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeAll() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_emptyFile_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = readerService.readFromFile(EMPTY_FILE_PATH);
        Assert.assertEquals("For empty file you should return empty list",
                expected, actual);
    }

    @Test
    public void readFromFile_fileWithData_ok() {
        List<String> expected = new ArrayList<>();
        expected.add(DATA_FROM_FILE_FIRST);
        expected.add(DATA_FROM_FILE_SECOND);
        List<String> actual = readerService.readFromFile(FILE_WITH_DATA_PATH);
        actual.remove(0);
        Assert.assertEquals("File wasn't read properly", expected, actual);
    }

    @Test
    public void readFromFile_removeColumnNames_ok() {
        List<String> actual = readerService.readFromFile(FILE_WITH_DATA_PATH);
        Assert.assertTrue("Result list shouldn't contain column names",
                actual.contains(COLUMN_NAMES));
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_incorrectPath_notOk() {
        readerService.readFromFile(INCORRECT_PATH);
    }
}
