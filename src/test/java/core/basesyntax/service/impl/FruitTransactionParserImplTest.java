package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static final String HEADER = "fruit,quantity";
    private static FruitTransactionParser fruitTransactionParser;

    @BeforeClass
    public static void setUp() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test
    public void parseTransactions_validData_ok() {
        final List<FruitTransaction> expected = new ArrayList<>();
        FruitTransaction firstTransaction = new FruitTransaction();
        firstTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstTransaction.setFruit("apple");
        firstTransaction.setQuantity(10);
        FruitTransaction secondTransaction = new FruitTransaction();
        secondTransaction.setOperation(FruitTransaction.Operation.RETURN);
        secondTransaction.setFruit("apple");
        secondTransaction.setQuantity(10);
        expected.add(firstTransaction);
        expected.add(secondTransaction);
        String input = HEADER
                + System.lineSeparator()
                + "b,apple,10"
                + System.lineSeparator()
                + "r,apple,10";
        List<FruitTransaction> actual = fruitTransactionParser.parseTransactions(input);
        Assert.assertEquals(expected, actual);
    }
}
