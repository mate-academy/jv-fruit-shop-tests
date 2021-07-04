package service.file;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    public static final String WRONG_FILE_PATH = "wrong\\file\\path.csv";
    public static final String NORMA_FILE_PATH = "src\\test\\resources\\readFromFileTest.csv";
    public static final List<String> EXPECTED_LIST = List.of("b,100,30", "a,10,20");
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFile_WrongPath_NotOk() {
        fileReader.readFile(WRONG_FILE_PATH);
    }

    @Test
    public void readFile_NormalFile_Ok() {
        List<String> expected = EXPECTED_LIST;
        List<String> actual = fileReader.readFile(NORMA_FILE_PATH);
        assertEquals(expected, actual);
    }
}
