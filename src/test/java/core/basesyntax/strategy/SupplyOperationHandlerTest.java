package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private SupplyOperationHandler supplyOperationHandler;

    @BeforeEach
    void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    void handleTransaction_existFruit_Ok() {
        Storage.getStorageFruit().put("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.SUPPLY, "banana", 30);
        supplyOperationHandler.handleTransaction(fruitTransaction);
        assertEquals(50, Storage.getStorageFruit().get("banana"));
    }

    @Test
    void handleTransaction_notExistFruit_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.SUPPLY, "banana", 31);
        supplyOperationHandler.handleTransaction(fruitTransaction);
        assertEquals(31, Storage.getStorageFruit().get("banana"));
    }

    @AfterEach
    void tearDown() {
        Storage.getStorageFruit().clear();
    }
}

