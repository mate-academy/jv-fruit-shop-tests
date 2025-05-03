package core.basesyntax.services;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFromFileServiceImplTest {
    private static ReadFromFileService readFromFileService;
    private static List<String> test;

    @BeforeClass
    public static void beforeAll() {
        readFromFileService = new ReadFromFileServiceImpl();
        test = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,apple,50",
                "r,banana,10");
    }

    @Test
    public void readFile_Ok() {
        List<String> listFromTestFile = readFromFileService
                .readFile("src/test/java/core/basesyntax/services/resources/ReadFromFileTest.csv");
        Assert.assertEquals(test, listFromTestFile);
    }

    @Test
    public void readFile_NullPath_NotOk() {
        try {
            readFromFileService.readFile(null);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("RuntimeException should be thrown when file name is null");
    }

    @Test
    public void readFile_NonExistentFile_NotOk() {
        try {
            readFromFileService.readFile("NotExistingFileName.csv");
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("RuntimeException should be thrown when file name is not existing");
    }
}
