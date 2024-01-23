package core.basesyntax.service.filework;

import static org.junit.Assert.assertEquals;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        try {
            Files.write(Path.of(PATH_TO_FILE),expected);
        } catch (IOException e) {
            throw new RuntimeException("Can't write file");
        }
        List<String> actual = getDataFromFile.getFromStorage(PATH_TO_FILE);
        assertEquals(expected.get(1), actual.get(0));
        assertEquals(expected.get(2), actual.get(1));
        try {
            new FileWriter(PATH_TO_FILE,false);
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file");
        }
    }

    @Test(expected = RuntimeException.class)
    public void getDataFromWrongPath_notOk() {
        getDataFromFile.getFromStorage(WRONG_PATH_TO_FILE);
    }

}
