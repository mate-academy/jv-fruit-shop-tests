package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    public static final String INPUT_FILE_PATH = "src/test/resources/fruits.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        Assert.assertEquals(expected, readerService.readFromFile(INPUT_FILE_PATH));
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notOk() {
        readerService.readFromFile("invalid/filepath");
    }
}
