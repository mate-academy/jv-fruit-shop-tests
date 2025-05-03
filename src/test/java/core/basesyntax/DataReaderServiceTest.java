package core.basesyntax;

import core.basesyntax.service.DataReaderService;
import core.basesyntax.service.impl.DataReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataReaderServiceTest {
    private static final List<String> EMPTY_LIST_RESULT = new ArrayList<>();
    private static final List<String> RESULT_FROM_FILE_WITH_INPUT_DATA =
            List.of("type,fruit,quantity", "b,orange,20", "b,pineapple,10",
            "s,orange,10", "p,orange,13", "r,apple,20", "p,pineapple,5", "s,pineapple,50");
    private static final String INVALID_PATH_TO_FILE =
            "C:/Users/Bob/project/src/main/resources/file.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/test/resources/emptyFile.csv";
    private static final String PATH_TO_FILE_WITH_INPUT_DATA =
            "src/test/resources/inputDataFile.csv";
    private static DataReaderService dataReaderService;

    @BeforeClass
    public static void beforeClass() {
        dataReaderService = new DataReaderServiceImpl();
    }

    @Test
    public void readData_fromEmptyFile_ok() {
        List<String> actualResult = dataReaderService.readData(PATH_TO_EMPTY_FILE);
        Assert.assertEquals("Incorrect result list from empty file",
                EMPTY_LIST_RESULT, actualResult);
    }

    @Test
    public void readData_fileWithInputData_ok() {
        List<String> actualFirstResult = dataReaderService.readData(PATH_TO_FILE_WITH_INPUT_DATA);
        Assert.assertEquals("Incorrect result list from the file. Should be ",
                RESULT_FROM_FILE_WITH_INPUT_DATA, actualFirstResult);
        Assert.assertEquals(RESULT_FROM_FILE_WITH_INPUT_DATA.size(), actualFirstResult.size());
    }

    @Test(expected = RuntimeException.class)
    public void readData_invalidPathToFile_exceptionExpected_ok() {
        dataReaderService.readData(INVALID_PATH_TO_FILE);
        Assert.assertTrue("You should throw a Runtime Exception", true);
    }
}
