package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceCsvImplTest {
    private static ReaderService readerService;
    private static List<String> data;

    @BeforeClass
    public static void beforeAll() {
        readerService = new ReaderServiceCsvImpl();
    }

    @Before
    public void setUp() {
        data = new ArrayList<>();
        data.add("b,banana,20");
        data.add("b,apple,20");
        data.add("s,banana,10");
        data.add("s,apple,10");
        data.add("p,banana,30");
        data.add("p,apple,30");
        data.add("r,banana,5");
        data.add("r,apple,5");
    }

    @Test
    public void readFromFile_Ok() {
        String filePath = "src/test/resources/input.csv";
        List<String> actual = readerService.readFromFile(filePath);
        List<String> expected = data;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_wrongFilePath_notOk() {
        String filePath = "";
        List<String> actual = readerService.readFromFile(filePath);
    }
}
