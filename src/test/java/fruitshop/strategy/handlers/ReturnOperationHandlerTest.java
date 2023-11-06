package fruitshop.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import fruitshop.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private OperationHandler returnOperationHandler = new ReturnOperationHandler();

    @Test
    void processReturnOperation_ok() {
        Storage.getStorage().put("apple", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 15);
        assertDoesNotThrow(() -> returnOperationHandler.calculateOperation(fruitTransaction));
        int actual = Storage.getStorage().get("apple");
        assertEquals(115, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }
}
