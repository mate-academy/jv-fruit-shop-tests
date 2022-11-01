package core.basesyntax.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitActionParser;
import core.basesyntax.dao.exception.NoSuchEnumValue;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
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

        expected = new ArrayList<>();

        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 12);
        expected.add(firstTransaction);

        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 50);
        expected.add(secondTransaction);

        FruitTransaction thirdTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "kiwi", 15);
        expected.add(thirdTransaction);

        FruitTransaction fourthTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 2);
        expected.add(fourthTransaction);
    }

    @Test
    public void parseAction_Ok() {
        List<FruitTransaction> actual = fruitActionParser.parseAction(activity);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void parseAction_NullActivity_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fruitActionParser.parseAction(null));
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

    @Test
    public void getEnumValue_NonExistOperation_NotOk() {
        assertThrows(NoSuchEnumValue.class, () ->
                FruitActionParserImpl.getEnumValue(""));
    }

    @AfterClass
    public static void clear() {
        Storage.fruits.clear();
    }
}
