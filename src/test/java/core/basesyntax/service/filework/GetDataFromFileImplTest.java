package core.basesyntax.service.filework;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class GetDataFromFileImplTest {
    private static final String PATH_TO_FILE = "src/test/resources/fileInputDataTest.csv";
    private static final String WRONG_PATH_TO_FILE = "src/test/java/resources/test.";
    private static AddDataToFile addDataToFile;
    private static GetDataFromFile getDataFromFile;

    @BeforeClass
    public static void beforeClass() {
        addDataToFile = new AddDataToFileImpl();
        getDataFromFile = new GetDataFromFileImpl();
    }

    @Test
    public void getFromStorage_getData_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,testBanana,25");
        expected.add("p,testApple,12");
        List<String> actual = getDataFromFile.getFromStorage(PATH_TO_FILE);
        assertEquals(expected.get(1), actual.get(0));
        assertEquals(expected.get(2), actual.get(1));
    }

    @Test(expected = RuntimeException.class)
    public void getDataFromWrongPath_not_ok() {
        getDataFromFile.getFromStorage(WRONG_PATH_TO_FILE);
    }
}
