package service.impl;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ReadDataService;

public class ReadDataServiceImplTest {
    private static final String EMPTY_FILE_PATH = "src/test/java/resources/emptyFile_test.csv";
    private static final String VALID_PATH = "src/test/java/resources/correctFile_test.csv";
    private static final String INVALID_PATH = "invalid_path";
    private static ReadDataService readDataService;

    @BeforeClass
    public static void beforeAll() {
        readDataService = new ReadDataServiceImpl();
    }

    @Test
    public void read_validPath_Ok() {
        List<String> expected = List.of(
                "b,banana,20", "b,apple,100", "s,banana,10", "s,apple,30");
        List<String> actual = readDataService.read(VALID_PATH);
        Assert.assertEquals("File wasn't read correctly!", expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_invalidPath_notOk() {
        readDataService.read(INVALID_PATH);
    }

    @Test
    public void read_emptyFilePath_OK() {
        List<String> actual = readDataService.read(EMPTY_FILE_PATH);
        Assert.assertTrue(actual.isEmpty());
    }
}
