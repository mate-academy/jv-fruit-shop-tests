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
        FruitTransaction appleTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 120);
        FruitTransaction bananaTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 100);
        balanceOperationHandler.calculateOperation(appleTransaction);
        balanceOperationHandler.calculateOperation(bananaTransaction);
        int appleActual = Storage.getStorage().get("apple");
        assertEquals(120, appleActual);
        int bananaActual = Storage.getStorage().get("banana");
        assertEquals(100, bananaActual);
        balanceOperationHandler.calculateOperation(appleTransaction);
        int secondAppleActual = Storage.getStorage().get("apple");
        assertEquals(120, secondAppleActual);
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }
}
