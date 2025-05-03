package fruitshop.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import fruitshop.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private OperationHandler supplyOperationHandler = new SupplyOperationHandler();

    @Test
    void processReturnOperation_ok() {
        Storage.getStorage().put("apple", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 15);
        assertDoesNotThrow(() -> supplyOperationHandler.calculateOperation(fruitTransaction));
        int actual = Storage.getStorage().get("apple");
        assertEquals(115, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }
}
