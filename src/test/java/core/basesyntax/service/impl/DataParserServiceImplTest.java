package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParserService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserServiceImplTest {
    private static final FruitTransaction.Operation BALANCE = FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation SUPPLY = FruitTransaction.Operation.SUPPLY;
    private static final FruitTransaction.Operation RETURN = FruitTransaction.Operation.RETURN;
    private static final FruitTransaction.Operation PURCHASE = FruitTransaction.Operation.PURCHASE;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static List<String> dataList;
    private static List<FruitTransaction> fruitTransactions;
    private static DataParserService dataParserService;

    @BeforeClass
    public static void beforeAll() {
        dataList = new ArrayList<>();
        fruitTransactions = new ArrayList<>();
        dataParserService = new DataParserServiceImpl();
    }

    @Before
    public void init() {
        dataList = Stream.of("type,fruit,quantity", "b,banana,20", "b,apple,100", "s,banana,100",
                        "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50")
                .collect(Collectors.toList());
        fruitTransactions = Stream.of(
                        new FruitTransaction(BALANCE, BANANA, 20),
                        new FruitTransaction(BALANCE, APPLE, 100),
                        new FruitTransaction(SUPPLY, BANANA, 100),
                        new FruitTransaction(PURCHASE, BANANA, 13),
                        new FruitTransaction(RETURN, APPLE, 10),
                        new FruitTransaction(PURCHASE, APPLE, 20),
                        new FruitTransaction(PURCHASE, BANANA, 5),
                        new FruitTransaction(SUPPLY, BANANA, 50))
                .collect(Collectors.toList());
    }

    @Test
    public void parseTransaction_ok() {
        List<FruitTransaction> actualTransactions = dataParserService.parseToTransaction(dataList);
        assertEquals(fruitTransactions.size(), actualTransactions.size());

        FruitTransaction actual;
        FruitTransaction expected;
        for (int i = 0; i < fruitTransactions.size(); ++i) {
            actual = actualTransactions.get(i);
            expected = fruitTransactions.get(i);
            assertEquals(expected.getFruit(), actual.getFruit());
            assertEquals(expected.getOperation(), actual.getOperation());
            assertEquals(expected.getQuantity(), actual.getQuantity());
        }
    }

    @Test (expected = RuntimeException.class)
    public void invalidString_notOk() {
        dataList.add("invalid_transaction^&!@");
        dataParserService.parseToTransaction(dataList);
    }

    @Test (expected = RuntimeException.class)
    public void nullString_notOk() {
        dataList.add(null);
        dataParserService.parseToTransaction(dataList);
    }

    @Test (expected = RuntimeException.class)
    public void emptyString_notOk() {
        dataList.add("");
        dataParserService.parseToTransaction(dataList);
    }

    @Test (expected = RuntimeException.class)
    public void negativeAmount_notOk() {
        dataList.add("b,banana,-10");
        dataParserService.parseToTransaction(dataList);
    }

    @Test (expected = RuntimeException.class)
    public void invalidFruit_notOk() {
        dataList.add("b,,-10");
        dataParserService.parseToTransaction(dataList);
    }

    @Test (expected = RuntimeException.class)
    public void invalidOperation_notOk() {
        dataList.add(",apple,-10");
        dataParserService.parseToTransaction(dataList);
    }

    @After
    public void tearDown() {
        fruitTransactions.clear();
        dataList.clear();
    }

}
