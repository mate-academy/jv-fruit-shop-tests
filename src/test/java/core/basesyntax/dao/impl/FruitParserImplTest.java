package core.basesyntax.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.dao.FruitParser;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitParserImplTest {
    private static FruitParser fruitParser;
    private String[] data;
    private List<FruitTransaction> expected;

    @BeforeClass
    public static void setUp() {
        fruitParser = new FruitParserImpl();
    }

    @Before
    public void init() {
        data = new String[]{"b,banana,100", "s,apple,200", "p,kiwi,300", "r,apple,1"};

        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 200);
        FruitTransaction thirdTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "kiwi", 300);
        FruitTransaction fourthTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 1);

        expected = List.of(firstTransaction, secondTransaction,
                thirdTransaction, fourthTransaction);
    }

    @Test
    public void parse_notOk() {
        List<FruitTransaction> actual = fruitParser.parse(data);
        assertNotEquals(expected.toString(), actual.toString());
    }

    @Test(expected = RuntimeException.class)
    public void parse_null() {
        fruitParser.parse(null);
    }

    @Test
    public void getEnum_balanceOperation_isOk() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = FruitParserImpl.getEnum("b");
        assertEquals(expected, actual);
    }

    @Test
    public void getEnum_purchaseOperation_isOk() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.PURCHASE;
        FruitTransaction.Operation actual = FruitParserImpl.getEnum("p");
        assertEquals(expected, actual);
    }

    @Test
    public void getEnum_returnOperation_isOk() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.RETURN;
        FruitTransaction.Operation actual = FruitParserImpl.getEnum("r");
        assertEquals(expected, actual);
    }

    @Test
    public void getEnum_supplyOperation_isOk() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        FruitTransaction.Operation actual = FruitParserImpl.getEnum("s");
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void getEnum_nonExistOperation_notOk() {
        FruitParserImpl.getEnum("");
    }
}
