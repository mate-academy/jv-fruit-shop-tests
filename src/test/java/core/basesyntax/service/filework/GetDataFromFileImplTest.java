package core.basesyntax.service.filework;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
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

    @Before
    public void setUp() {
        File file = new File(PATH_TO_FILE);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create new test file");
        }
    }

    @After
    public void tearDown() {
        try {
            Files.delete(Path.of(PATH_TO_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete test file");
        }
    }

    @Test
    public void getData_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,testBanana,25");
        expected.add("p,testApple,12");
        try {
            for (int i = 0; i < expected.size(); i++) {
                StringBuilder builder = new StringBuilder();
                builder.append(expected.get(i)).append(System.lineSeparator());
                Files.write(Path.of(PATH_TO_FILE), builder
                        .toString().getBytes(), StandardOpenOption.APPEND);
                builder.delete(0, builder.length());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to test file");
        }
        List<String> actual = getDataFromFile.getFromStorage(PATH_TO_FILE);
        assertEquals(expected.get(1), actual.get(0));
        assertEquals(expected.get(2), actual.get(1));
    }

    @Test(expected = RuntimeException.class)
    public void getDataFromWrongPath_not_ok() {
        getDataFromFile.getFromStorage(WRONG_PATH_TO_FILE);
    }
}
