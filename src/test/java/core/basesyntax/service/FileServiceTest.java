package core.basesyntax.service;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceTest {
    private static FileServiceImpl fileService;

    @BeforeClass
    public static void beforeClass() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void read_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20");
        List<String> actual = fileService.read("src/test/resources/input.csv");
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void readInvalidFilePath_NotOk() {
        fileService.read("src/test/resources/Invalid");
    }

    @Test
    public void write_Ok() {
        List<String> expected = List.of("Some_report");
        fileService.write("src/test/resources/reportTestOk.csv", "Some_report");
        List<String> actual = fileService.read("src/test/resources/reportTestOk.csv");
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void writeToInvalidFilePath_NotOk() {
        fileService.write("invalid/src/test/resources/", "Some_report");
    }

    @Test(expected = RuntimeException.class)
    public void writeNullData_NotOk() {
        fileService.write("src/test/resources/reportTest.csv", null);
    }
}
