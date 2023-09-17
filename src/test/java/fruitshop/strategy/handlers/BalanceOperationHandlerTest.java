package fruitshop.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import fruitshop.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private OperationHandler balanceOperationHandler = new BalanceOperationHandler();

    @Test
    void processBalanceOperation_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 120);
        balanceOperationHandler.calculateOperation(fruitTransaction);
        int actual = Storage.getStorage().get("apple");
        assertEquals(120, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }
}
