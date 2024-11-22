package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import fruitshop.strategy.OperationHandler;
import fruitshop.strategy.impl.BalanceOperationHandler;
import fruitshop.strategy.impl.PurchaseOperationHandler;
import fruitshop.strategy.impl.ReturnOperationHandler;
import fruitshop.strategy.impl.SupplyOperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class OperationHandlerTest {
    @AfterEach
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void balanceOperationHandler_ok() {
        OperationHandler handler = new BalanceOperationHandler();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100);

        handler.apply(transaction);

        assertEquals(100, Storage.fruits.get("apple"));
    }

    @Test
    public void purchaseOperationHandler_enoughStock_ok() {
        Storage.fruits.put("apple", 100);
        OperationHandler handler = new PurchaseOperationHandler();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 50);

        handler.apply(transaction);

        assertEquals(50, Storage.fruits.get("apple"));
    }

    @Test
    public void purchaseOperationHandler_notEnoughStock_notOk() {
        Storage.fruits.put("apple", 10);
        OperationHandler handler = new PurchaseOperationHandler();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> handler.apply(transaction));
        assertEquals("Not enough stock to purchase 20 apple", exception.getMessage());
    }

    @Test
    public void returnOperationHandler_ok() {
        Storage.fruits.put("apple", 100);
        OperationHandler handler = new ReturnOperationHandler();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 30);

        handler.apply(transaction);

        assertEquals(130, Storage.fruits.get("apple"));
    }

    @Test
    public void supplyOperationHandler_ok() {
        Storage.fruits.put("banana", 50);
        OperationHandler handler = new SupplyOperationHandler();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 20);

        handler.apply(transaction);

        assertEquals(70, Storage.fruits.get("banana"));
    }
}
