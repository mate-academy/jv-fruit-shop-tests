package core.basesyntax.dao;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    public static final String EMPTY_FILE_PATH = "src/test/java/resources/empty_input_test.csv";
    public static final String FILE_WITH_DATA_PATH
            = "src/test/java/resources/input_with_data_test.csv";
    public static final String INCORRECT_PATH = "wrong path";
    public static final String COLUMN_NAMES = "operation,fruit,amount";
    public static final String DATA_FROM_FILE_FIRST = "b,banana,40";
    public static final String DATA_FROM_FILE_SECOND = "b,apple,35";
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_emptyFile_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.read(EMPTY_FILE_PATH);
        Assert.assertEquals("For empty file you should return empty list",
                expected, actual);
    }

    @Test
    public void readFromFile_fileWithData_ok() {
        List<String> expected = new ArrayList<>();
        expected.add(DATA_FROM_FILE_FIRST);
        expected.add(DATA_FROM_FILE_SECOND);
        List<String> actual = fileReader.read(FILE_WITH_DATA_PATH);
        Assert.assertEquals("File wasn't read properly", expected, actual);
    }

    @Test
    public void readFromFile_removeColumnNames_ok() {
        List<String> actual = fileReader.read(FILE_WITH_DATA_PATH);
        Assert.assertFalse("Result list shouldn't contain column names",
                actual.contains(COLUMN_NAMES));
    }

    @Test
    public void readFromFile_incorrectPath_notOk() {
        Assert.assertThrows("You should throw an exception for incorrect file path",
                RuntimeException.class, () -> fileReader.read(INCORRECT_PATH));
    }
}
