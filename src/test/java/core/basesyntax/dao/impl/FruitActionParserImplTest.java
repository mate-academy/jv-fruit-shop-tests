package core.basesyntax.dao.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitActionParser;
import core.basesyntax.dao.exception.NoSuchEnumValue;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitActionParserImplTest {
    private static FruitActionParser fruitActionParser;
    private String[] activity;
    private List<FruitTransaction> expected;

    @BeforeClass
    public static void setUp() {
        fruitActionParser = new FruitActionParserImpl();
    }

    @Before
    public void init() {
        activity = new String[]{"b,banana,12", "s,apple,50", "p,kiwi,15", "r,apple,2"};

        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 12);
        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50);
        FruitTransaction thirdTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "kiwi", 15);
        FruitTransaction fourthTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 2);

        expected = List.of(firstTransaction, secondTransaction,
                thirdTransaction, fourthTransaction);
    }

    @Test
    public void parseAction_Ok() {
        List<FruitTransaction> actual = fruitActionParser.parseAction(activity);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test(expected = RuntimeException.class)
    public void parseAction_NullActivity() {
        fruitActionParser.parseAction(null);
    }

    @Test
    public void getEnumValue_BalanceOperation_Ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = FruitActionParserImpl.getEnumValue("b");
        assertEquals(expected, actual);
    }

    @Test
    public void getEnumValue_PurchaseOperation_Ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.PURCHASE;
        FruitTransaction.Operation actual = FruitActionParserImpl.getEnumValue("p");
        assertEquals(expected, actual);
    }

    @Test
    public void getEnumValue_ReturnOperation_Ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.RETURN;
        FruitTransaction.Operation actual = FruitActionParserImpl.getEnumValue("r");
        assertEquals(expected, actual);
    }

    @Test
    public void getEnumValue_SupplyOperation_Ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        FruitTransaction.Operation actual = FruitActionParserImpl.getEnumValue("s");
        assertEquals(expected, actual);
    }

    @Test(expected = NoSuchEnumValue.class)
    public void getEnumValue_NonExistOperation_NotOk() {
        FruitActionParserImpl.getEnumValue("");
    }

    @AfterClass
    public static void clear() {
        Storage.fruits.clear();
    }
}
