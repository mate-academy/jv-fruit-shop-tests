package core.basesyntax.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String inputFileName = "src/test/java/resources/testFruitStore.csv";
    private static FileReaderServiceImpl fileReaderService;

    @BeforeClass
    public static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_correctPath_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        List<String> actual = fileReaderService.readFromFile(inputFileName);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_null_notOk() {
        fileReaderService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_uncorrectedPath_notOk() {
        fileReaderService.readFromFile("badPath");
    }
}
