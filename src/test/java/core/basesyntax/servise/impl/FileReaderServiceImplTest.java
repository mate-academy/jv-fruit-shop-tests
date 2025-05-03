package core.basesyntax.servise.impl;

import core.basesyntax.servise.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void read_fileWithExistingPath_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        List<String> actual = fileReaderService.read("src/main/resources/data.csv");
        Assert.assertTrue(expected.containsAll(actual));
        Assert.assertEquals(expected.size(), actual.size());
    }

    @Test
    public void read_fileWithNullPath_notOk() {
        try {
            fileReaderService.read(null);
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }

    @Test
    public void read_fileWithEmptyPath_notOk() {
        try {
            fileReaderService.read("");
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }

    @Test
    public void read_fileWithNonExistingPath_notOk() {
        try {
            fileReaderService.read("src/main/resources/noSuchPath.csv");
        } catch (RuntimeException e) {
            return;
        }
        Assert.fail("Should be thrown RuntimeException");
    }
}
