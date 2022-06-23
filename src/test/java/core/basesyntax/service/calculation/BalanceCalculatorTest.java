package core.basesyntax.service.calculation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceCalculatorTest {
    private static TransactionCalculation transactionCalculation;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        transactionCalculation = new BalanceCalculator();
    }

    @Test
    void balanceCalculator_Ok() {
        fruitTransaction = new FruitTransaction("b", "banana", 10);
        transactionCalculation.calculate(fruitTransaction);
        Integer actual = Storage.storage.get("banana");
        Integer expected = 10;
        Assert.assertEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }
}
