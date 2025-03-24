package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void testSupplyOperation() {
        OperationHandler handler = new SupplyOperation();

        // Initial supply
        handler.handle(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "apple",
                100
        ));
        assertEquals(100, Storage.storage.get("apple").intValue());

        // Additional supply
        handler.handle(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "apple",
                50
        ));
        assertEquals(150, Storage.storage.get("apple").intValue());
    }
}
