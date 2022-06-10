package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String TRANSACTION_EMPTY = "src/test/resources/transaction_empty.csv";
    private static final String TRANSACTION_FULL = "src/test/resources/transaction.csv";
    private static final String INVALID_PATH = "src/test/resources/invalid_path.csv";
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_emptyCsvFile_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = readerService.readFromFile(TRANSACTION_EMPTY);
        assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_validCsvFile_ok() {
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
        List<String> actual = readerService.readFromFile(TRANSACTION_FULL);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_invalidFilePath_notOk() {
        readerService.readFromFile(INVALID_PATH);
    }
}
