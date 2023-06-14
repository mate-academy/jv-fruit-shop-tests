package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class OperationHandlerTest {
    private static OperationHandler operationHandler;

    @Test
    void executeOperation_balanceHandler_Ok() {
        operationHandler = new BalanceHandler();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(20);

        assertEquals(20, operationHandler.executeOperation(fruitTransaction));
        assertEquals(1, Storage.storage.size());
        assertEquals(20, Storage.storage.get(fruitTransaction.getFruit()));
    }

    @Test
    void executeOperation_supplyHandler_Ok() {
        operationHandler = new SupplyHandler();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setQuantity(30);
        Storage.storage.put("apple", 20);

        assertEquals(50, operationHandler.executeOperation(fruitTransaction));
        assertEquals(1, Storage.storage.size());
        assertEquals(50, Storage.storage.get(fruitTransaction.getFruit()));
    }

    @Test
    void executeOperation_returnHandler_Ok() {
        operationHandler = new ReturnHandler();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setQuantity(5);
        Storage.storage.put("apple", 50);

        assertEquals(55, operationHandler.executeOperation(fruitTransaction));
        assertEquals(1, Storage.storage.size());
        assertEquals(55, Storage.storage.get(fruitTransaction.getFruit()));
    }

    @Test
    void executeOperation_purchaseHandler_Ok() {
        operationHandler = new PurchaseHandler();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setQuantity(25);
        Storage.storage.put("apple", 55);

        assertEquals(30, operationHandler.executeOperation(fruitTransaction));
        assertEquals(1, Storage.storage.size());
        assertEquals(30, Storage.storage.get(fruitTransaction.getFruit()));
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
