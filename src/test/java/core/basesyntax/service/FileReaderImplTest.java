package core.basesyntax.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;
    private static final String FILE_PATH = "src/main/resources/input.csv";
    private static final String EMPTY_FILE = "src/main/resources/emptyFile.csv";
    private static final String EXISTENT = "src/main/resources/existent.csv";
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
        expected = new ArrayList<>();
    }

    @Test
    public void getDataFromFile_RightPath_Ok() {
        fileReader.read(FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void readEmptyFile() {
        fileReader.read(EMPTY_FILE);
    }

    @Test
    public void getDateFromFile() {
        expected.add("type,fruit,quantity");
        expected.add("s,banana,10");
        expected.add("r,apple,20");
    }

    @Test(expected = RuntimeException.class)
    public void readFromExistentFile() {
        fileReader.read(EXISTENT);
    }
}
