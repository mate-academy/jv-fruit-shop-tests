package core.basesyntax;

import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static List<String> excepted;
    private static final File TEST_FILE = new File("src/test/resources/fileForFileReaderTest.csv");
    private static final File NON_EXISTENT_FILE = new File("src/test/resources/bandera.csv");

    @BeforeClass
    public static void beforeClass() throws Exception {
        excepted = new ArrayList<>();
        excepted.add("type,fruit,quantity");
        excepted.add("b,durian,100");
        excepted.add("b,papaya,55");
        excepted.add("p,durian,28");
        excepted.add("s,papaya,45");
    }

    @Test
    public void readFromFile_validInputData_ok() {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        List<String> actual = fileReaderService.readFromFile(TEST_FILE);
        for (int i = 0; i < excepted.size(); i++) {
            Assert.assertEquals(excepted.get(i), actual.get(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void name() {
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        fileReaderService.readFromFile(NON_EXISTENT_FILE);
    }
}
