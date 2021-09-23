package core.basesyntax.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;
    private static final String FILE_PATH = "src/main/resources/fruitbase.csv";
    private static final String WRONG_FILE_PATH = "error/23op/hello.csv";
    private static final String EMPTY_FILE = "src/test/resources/emptyFile.csv";
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileReader = new FileReaderImpl();
        expected = new ArrayList<>();
    }

    @Test
    public void getDataFromFile_RightPath_ok() {
        fileReader.getDataFromFile(FILE_PATH);
    }

    @Test
    public void getDataFromFile_ok() {
        expected.add("type,fruit,quantity");
        expected.add("b,banana,10");
        expected.add("r,apple,10");
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
