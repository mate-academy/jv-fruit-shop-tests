package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static FruitTransactionParser fruitTransactionParser;

    @BeforeClass
    public static void setUp() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test (expected = NullPointerException.class)
    public void toTransactions_dataIsNull_notOk() {
        fruitTransactionParser.toTransactions(null);
    }

    @Test
    public void toTransactions_validData_ok() {
        String data = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator()
                + "p,banana,6" + System.lineSeparator()
                + "r,banana,3";
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 6));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 3));
        List<FruitTransaction> actual = fruitTransactionParser.toTransactions(data);
        assertEquals(expected, actual);
    }
}
