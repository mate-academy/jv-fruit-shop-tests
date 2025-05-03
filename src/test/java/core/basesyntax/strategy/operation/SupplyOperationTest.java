package core.basesyntax.strategy.operation;

import core.basesyntax.data.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private final SupplyOperation supplyOperation = new SupplyOperation();

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void apply_validSupplyTransaction_addsToStock() {
        Storage.add("orange", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "orange", 15);
        supplyOperation.apply(transaction);

        Assertions.assertEquals(25, Storage.getInventory().get("orange"));
    }
}
