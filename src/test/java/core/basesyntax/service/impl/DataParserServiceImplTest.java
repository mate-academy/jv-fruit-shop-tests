package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.StorageTransaction;
import core.basesyntax.service.DataParserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserServiceImplTest {
    private static final List<String> EMPTY_LIST = Collections.emptyList();
    private static final List<String> DATA = new ArrayList<>();
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static List<StorageTransaction> storageTransactions;
    private static DataParserService parserService;

    @BeforeClass
    public static void setUp() {
        parserService = new DataParserServiceImpl();
        storageTransactions = new ArrayList<>();
        storageTransactions.add(new StorageTransaction(
                StorageTransaction.Operation.BALANCE, BANANA, 20));
        storageTransactions.add(new StorageTransaction(
                StorageTransaction.Operation.BALANCE, APPLE, 100));
        storageTransactions.add(new StorageTransaction(
                StorageTransaction.Operation.SUPPLY, BANANA, 100));
        storageTransactions.add(new StorageTransaction(
                StorageTransaction.Operation.PURCHASE, BANANA, 13));
        storageTransactions.add(new StorageTransaction(
                StorageTransaction.Operation.RETURN, APPLE, 10));
        storageTransactions.add(new StorageTransaction(
                StorageTransaction.Operation.PURCHASE, APPLE, 20));
        storageTransactions.add(new StorageTransaction(
                StorageTransaction.Operation.PURCHASE, BANANA, 5));
        storageTransactions.add(new StorageTransaction(
                StorageTransaction.Operation.SUPPLY, BANANA, 50));
        DATA.add("type,fruit,quantity");
        DATA.add("b,banana,20");
        DATA.add("b,apple,100");
        DATA.add("s,banana,100");
        DATA.add("p,banana,13");
        DATA.add("r,apple,10");
        DATA.add("p,apple,20");
        DATA.add("p,banana,5");
        DATA.add("s,banana,50");
    }

    @Test(expected = RuntimeException.class)
    public void parse_emptyMethodParameter_notOk() {
        parserService.parse(EMPTY_LIST);
        fail("You should trow RuntimeException when method parameter is empty");
    }

    @Test
    public void parse_returnCorrectList_ok() {
        List<StorageTransaction> actual = parserService.parse(DATA);
        int actualSize = actual.size();
        assertEquals("Expected size: " + storageTransactions.size() + " but was " + actualSize,
                storageTransactions.size(), actualSize);
        assertEquals("Expected data: " + storageTransactions.toString() + " but was " + actual,
                storageTransactions, actual);
    }
}
