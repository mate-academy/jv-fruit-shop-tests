package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;
    private static final String FILE_PATH = "src/test/resources/fruitbase.csv";
    private static final String WRONG_FILE_PATH = "error/23op/hello.csv";
    private static final String EMPTY_FILE = "src/test/resources/emptyFile.csv";
    private static List<String> expected;
    private static List<String> actual;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileReader = new FileReaderImpl();
        expected = new ArrayList<>();
    }

    @Before
    public void setUp() throws Exception {
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
    }

    @Test
    public void getDataFromFile_ok() {
        actual = fileReader.getDataFromFile(FILE_PATH);
        assertEquals(expected.get(0), actual.get(0));
        actual = fileReader.getDataFromFile(FILE_PATH);
        assertEquals(expected.get(1), actual.get(1));
    }

    @Test(expected = RuntimeException.class)
    public void getDataFromFile_WithWrongPath_notOk() {
        fileReader.getDataFromFile(WRONG_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void getDataFromEmptyFile_notOk() {
        fileReader.getDataFromFile(EMPTY_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void getDataFromNullFile_notOk() {
        fileReader.getDataFromFile(null);
    }
}
