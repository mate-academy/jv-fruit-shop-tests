package core.basesyntax.java.core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.java.core.basesyntax.service.impl.ReaderServiceCsvImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReaderServiceTest {
    private static final String TRANSACTION_EMPTY = "src/test/java/resources/transaction_empty.csv";
    private static final String TRANSACTION_FULL = "src/test/java/resources/transaction.csv";
    private static ReaderService readerService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        readerService = new ReaderServiceCsvImpl();
    }

    @Test
    public void readFromFile_emptyCsvFile_notOk() {
        exception.expect(RuntimeException.class);
        readerService.readFromFile(TRANSACTION_EMPTY);
    }

    @Test
    public void readFromFile_ok() {
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
}
