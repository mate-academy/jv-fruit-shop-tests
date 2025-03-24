package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    @BeforeEach
    void setUp() {
        Storage.storage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void testSupplyOperation() {
        OperationHandler handler = new SupplyOperation();
        Storage.storage.put("apple", 0);

        handler.handle(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "apple",
                100
        ));
        handler.handle(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "apple",
                50
        ));

        assertEquals(150, Storage.storage.get("apple").intValue());
    }
}
