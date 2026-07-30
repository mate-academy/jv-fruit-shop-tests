package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private BalanceOperation balanceOperation = new BalanceOperation();

    @AfterEach
    void tearDown() {
        Storage.getFruits().clear();
    }

    @Test
    void apply_validTransaction_addsFruitToStorage() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100);
        balanceOperation.apply(fruitTransaction);

        int actualQuantity = Storage.getFruits().get("apple");
        assertEquals(100, actualQuantity,
                "Storage should contain 100 apples after BALANCE operation");
    }

    @Test
    void apply_negativeQuantity_throwsRuntimeException() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", -100);
        Assertions.assertThrows(RuntimeException.class, () -> {
            balanceOperation.apply(fruitTransaction);
        }, "Balance operation should throw exception if quantity is less than 0");
    }
}
