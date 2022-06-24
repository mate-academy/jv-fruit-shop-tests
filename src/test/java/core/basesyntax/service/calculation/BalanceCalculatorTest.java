package core.basesyntax.service.calculation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceCalculatorTest {
    private static TransactionCalculation transactionCalculation;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        transactionCalculation = new BalanceCalculator();
    }

    @Test
    public void balanceCalculator_Ok() {
        fruitTransaction = new FruitTransaction("b", "banana", 10);
        transactionCalculation.calculate(fruitTransaction);
        Integer actual = Storage.storage.get("banana");
        Integer expected = 10;
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.storage.clear();
    }
}
