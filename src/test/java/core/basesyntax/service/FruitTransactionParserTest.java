package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserTest {
    private static FruitTransactionParser fruitTransactionParser;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test(expected = RuntimeException.class)
    public void getFruitTransactionsList_NullInput_NotOk() {
        fruitTransactionParser.getFruitTransactionsList(null);
    }

    @Test(expected = RuntimeException.class)
    public void getFruitTransactionsList_EmptyInput_NotOk() {
        List<String> emptyList = new ArrayList<>();
        fruitTransactionParser.getFruitTransactionsList(emptyList);
    }

    @Test
    public void getFruitTransactionsList_ValidInput_Ok() {
        FruitTransaction bananaTransaction = new FruitTransaction();
        bananaTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        bananaTransaction.setFruit("banana");
        bananaTransaction.setQuantity(100);
        FruitTransaction appleTransaction = new FruitTransaction();
        appleTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        appleTransaction.setFruit("apple");
        appleTransaction.setQuantity(100);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(bananaTransaction);
        expected.add(appleTransaction);
        List<String> validTransactions = List.of("header line", "b,banana,100", "b,apple,100");
        List<FruitTransaction> actual = fruitTransactionParser
                .getFruitTransactionsList(validTransactions);
        Assert.assertEquals(actual, expected);
    }
}
