package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String INPUT_PATH = "src/test/java/resourcesTest/inputFileTest.csv";
    private static final String EMPTY_FILE = "src/test/java/resourcesTest/empty.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void read_file_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = fileReaderService.readFile(INPUT_PATH);
        Assert.assertNotNull(actual);
        Assert.assertEquals("Expected: " + expected + ", but " + actual, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_file_NullPath_notOk() {
        fileReaderService.readFile(null);
    }

    @Test
    public void read_file_emptyFile_ok() {
        List<String> actual = fileReaderService.readFile(EMPTY_FILE);
        Assert.assertNotNull(actual);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void read_file_emptyPath_notOk() {
        fileReaderService.readFile("");
    }
}
