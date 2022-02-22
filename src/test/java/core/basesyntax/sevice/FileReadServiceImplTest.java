package core.basesyntax.sevice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReadServiceImplTest {
    private static final String TEST1_FILE_NAME = "src/test/resourses_tests/Test1_InputData.csv";
    private static final String EMPTY_FILE = "src/test/resourses_tests/Test2_Empty.csv";
    private static final String NON_EXIST_FILE = "src/test/resourses_tests/Non-exist.csv";
    private static FileReadService fileReadService;
    private static List<String> testData;

    @BeforeClass
    public static void beforeClass() {
        fileReadService = new FileReadServiceImpl();
        testData = new ArrayList<>();
        testData.add("type,fruit,quantity");
        testData.add("b,banana,20");
        testData.add("r,apple,10");
        testData.add("s,banana,50");
    }

    @Test(expected = RuntimeException.class)
    public void readDataFromFile_NonExistFile_NotOk() {
        fileReadService.readDataFromFile(NON_EXIST_FILE);
    }

    @Test
    public void readDataFromFile_emptyFile_NotOk() {
        assertTrue(fileReadService.readDataFromFile(EMPTY_FILE).isEmpty());
    }

    @Test
    public void readDataFromFile_TestFile_Ok() {
        assertEquals(testData, fileReadService.readDataFromFile(TEST1_FILE_NAME));
    }
}
