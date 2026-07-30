package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private SupplyOperation supplyOperation = new SupplyOperation();

    @AfterEach
    void tearDown() {
        Storage.getFruits().clear();
    }

    @Test
    void apply_validSupplyTransaction_increasesFruitQuantity() {
        Storage.getFruits().put("banana", 20);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 30);

        supplyOperation.apply(transaction);
        int actualQuantity = Storage.getFruits().get("banana");
        Assertions.assertEquals(50, actualQuantity,
                "Quantity should be 20 + 30 = 50 after supply");

    }

    @Test
    void apply_negativeQuantitySupplyTransaction_throwRuntimeException() {
        Storage.getFruits().put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", -20);
        Assertions.assertThrows(RuntimeException.class, () -> {
            supplyOperation.apply(transaction);
        }, "Supply operation should throw exception if quantity is less than 0");
    }
}
