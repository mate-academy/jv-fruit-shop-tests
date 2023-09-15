package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Warehouse;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import strategy.impl.BalanceTransactionImpl;
import strategy.impl.PurchaseTransactionImpl;
import strategy.impl.ReturnTransactionImpl;
import strategy.impl.SupplyTransactionImpl;

class TransactionHandlerTest {
    @AfterEach
    void tearDown() {
        Warehouse.STORAGE.clear();
    }

    @Test
    void handleTransaction_addToStorage_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(100);
        new BalanceTransactionImpl().handleTransaction(fruitTransaction);
        assertEquals(100, Warehouse.STORAGE.get("apple"));
        new PurchaseTransactionImpl().handleTransaction(fruitTransaction);
        assertEquals(0, Warehouse.STORAGE.get("apple"));
        new SupplyTransactionImpl().handleTransaction(fruitTransaction);
        assertEquals(100, Warehouse.STORAGE.get("apple"));
        new ReturnTransactionImpl().handleTransaction(fruitTransaction);
        assertEquals(200, Warehouse.STORAGE.get("apple"));
    }

    @Test
    void handleTransaction_null_notOk() {
        assertThrows(NullPointerException.class,
                () -> new BalanceTransactionImpl().handleTransaction(null));
        assertThrows(NullPointerException.class,
                () -> new PurchaseTransactionImpl().handleTransaction(null));
        assertThrows(NullPointerException.class,
                () -> new SupplyTransactionImpl().handleTransaction(null));
        assertThrows(NullPointerException.class,
                () -> new ReturnTransactionImpl().handleTransaction(null));
    }
}
