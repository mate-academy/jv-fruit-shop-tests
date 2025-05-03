package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String INPUT_FILE = "src/test/resources/input.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_checkFile_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = readerService.readFromFile(INPUT_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullFile_notOK() {
        readerService.readFromFile(null);
    }
}
