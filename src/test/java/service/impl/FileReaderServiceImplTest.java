package service.impl;

import db.Storage;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FileReaderService;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeAll() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,100",
                "p,banana,20",
                "s,banana,15",
                "s,banana,13",
                "r,banana,100",
                "b,apple,100",
                "r,apple,10",
                "p,apple,20");
        List<String> actual = fileReaderService.readFromFile("src/test/resources/fruits.csv");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notOk() {
        fileReaderService.readFromFile("");
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
