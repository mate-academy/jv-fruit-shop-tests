package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParserService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserServiceImplTest {
    private static TransactionParserService parserService;
    private static List<String> data;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        parserService = new TransactionParserServiceImpl();
        fruit = new Fruit("banana");
    }

    @Before
    public void setUp() {
        data = new ArrayList<>();
    }

    @Test
    public void parseTransaction_validData_ok() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, fruit, 50);
        FruitTransaction supplyTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, fruit, 10);
        FruitTransaction purchaseTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, fruit, 20);
        FruitTransaction returnTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, fruit, 5);
        final List<FruitTransaction> expected = List.of(balanceTransaction, supplyTransaction,
                purchaseTransaction, returnTransaction);
        data.add("column_name");
        data.add("b,banana,50");
        data.add("s,banana,10");
        data.add("p,banana,20");
        data.add("r,banana,5");
        List<FruitTransaction> actual = parserService.parseTransaction(data);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseTransaction_dataIsNull_notOk() {
        parserService.parseTransaction(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void parseTransaction_nonExistOperation_notOk() {
        data.add("column_name");
        data.add("q,apple,20");
        parserService.parseTransaction(data);
    }

    @After
    public void tearDown() {
        data.clear();
    }
}
