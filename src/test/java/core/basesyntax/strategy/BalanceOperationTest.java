package core.basesyntax.strategy;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static OperationHandler balanceOperation;

    @BeforeAll
    public static void setUp() {
        balanceOperation = new BalanceOperation();
    }

    @Test
    void test_apply_positiveQuantity_successfullyStored() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);

        balanceOperation.apply(fruitTransaction);

        assertEquals(10, Storage.getFruitQuantity("apple"));
    }

    @Test
    void test_apply_negativeQuantity_throwsException() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", -5);

        assertThrows(RuntimeException.class, () -> balanceOperation.apply(fruitTransaction));
    }
}
