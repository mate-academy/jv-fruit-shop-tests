package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static FruitTransactionParser fruitTransactionParser;

    @BeforeClass
    public static void setUp() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test
    public void toTransactions_validInputData_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "pineapple", 30));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "pineapple", 25));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "pineapple", 50));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10));

        String validData = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "b,pineapple,30" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,pineapple,25" + System.lineSeparator()
                + "p,banana,13" + System.lineSeparator()
                + "s,pineapple,50" + System.lineSeparator()
                + "r,apple,10" + System.lineSeparator();
        List<FruitTransaction> actual = fruitTransactionParser.toTransactions(validData);
        Assert.assertEquals("Output data is wrong", actual, expected);
    }

    @Test(expected = NullPointerException.class)
    public void toTransactions_inputDataNull_notOk() {
        fruitTransactionParser.toTransactions(null);
    }
}
