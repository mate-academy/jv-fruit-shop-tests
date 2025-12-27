package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private ReturnOperationHandler returnOperationHandler;

    @BeforeEach
    void setUp() {
        returnOperationHandler = new ReturnOperationHandler();

    }

    @Test
    void handleTransaction_existFruit_Ok() {
        Storage.getStorageFruit().put("banana", 40);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.RETURN, "banana", 30);
        returnOperationHandler.handleTransaction(fruitTransaction);
        assertEquals(70, Storage.getStorageFruit().get("banana"));
    }

    @Test
    void handleTransaction_notExistFruit_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.RETURN, "banana", 21);
        returnOperationHandler.handleTransaction(fruitTransaction);
        assertEquals(21, Storage.getStorageFruit().get("banana"));
    }

    @AfterEach
    void tearDown() {
        Storage.getStorageFruit().clear();
    }
}

