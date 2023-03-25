package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/input.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty.csv";
    private ReaderServiceImpl readerService;
    private List<String> records;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
        records = new ArrayList<>();
        records.add("type,fruit,quantity");
        records.add("b,banana,20");
        records.add("b,apple,100");
        records.add("s,banana,102");
        records.add("p,banana,13");
        records.add("r,apple,10");
        records.add("p,apple,20");
        records.add("p,banana,5");
        records.add("s,banana,50");
    }

    @Test
    public void readerService_readFromFile_Ok() {
        List<String> actualResult = readerService.read(INPUT_FILE_PATH);
        Assert.assertEquals("Test failed! You should returned next record "
                        + records.get(0) + " but you returned "
                        + actualResult.get(0),
                records, actualResult);
    }

    @Test
    public void readerService_readFromEmptyFile_Ok() {
        List<String> actualResult = readerService.read(EMPTY_FILE_PATH);
        records = new ArrayList<>();
        Assert.assertEquals("Test failed! You should returned empty record.",
                records, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void readerService_readFromEmptyPath_notOk() {
        readerService.read("");
    }
}
