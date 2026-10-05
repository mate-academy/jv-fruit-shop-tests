package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class OperationHandlersTest {
    @AfterEach
    void clearStorage() {
        Storage.fruits.clear();
    }

    @Test
    void balanceOperation_ok() {
        OperationHandler handler = new BalanceOperation();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, "apple", 50);
        handler.handle(transaction);
        assertEquals(50, Storage.fruits.get("apple"));
    }

    @Test
    void supplyOperation_ok() {
        Storage.fruits.put("banana", 20);
        OperationHandler handler = new SupplyOperation();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, "banana", 30);
        handler.handle(transaction);
        assertEquals(50, Storage.fruits.get("banana"));
    }

    @Test
    void returnOperation_ok() {
        Storage.fruits.put("kiwi", 10);
        OperationHandler handler = new ReturnOperation();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.RETURN, "kiwi", 5);
        handler.handle(transaction);
        assertEquals(15, Storage.fruits.get("kiwi"));
    }

    @Test
    void purchaseOperation_validQuantity_ok() {
        Storage.fruits.put("orange", 20);
        OperationHandler handler = new PurchaseOperation();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "orange", 15);
        handler.handle(transaction);
        assertEquals(5, Storage.fruits.get("orange"));
    }

    @Test
    void purchaseOperation_notEnoughStock_notOk() {
        Storage.fruits.put("lemon", 10);
        OperationHandler handler = new PurchaseOperation();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "lemon", 15);
        assertThrows(RuntimeException.class, () -> handler.handle(transaction));
    }
}
