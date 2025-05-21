package basesyntax.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import basesyntax.model.FruitTransaction;
import basesyntax.model.Operation;
import basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private OperationHandler purchase;

    @BeforeEach
    void setUp() {
        purchase = new PurchaseOperation();
        Storage.clear();
    }

    @Test
    void handle_validPurchaseOperation_Ok() {
        Storage.put("apple", 50);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 20);
        purchase.handle(transaction);
        assertEquals(30, Storage.get("apple"));
    }

    @Test
    void handle_moreThenOneValidPurchaseOperation_Ok() {
        Storage.put("kiwi", 100);
        FruitTransaction transaction1 = new FruitTransaction(Operation.PURCHASE, "kiwi", 20);
        FruitTransaction transaction2 = new FruitTransaction(Operation.PURCHASE, "kiwi", 50);
        purchase.handle(transaction1);
        assertEquals(80, Storage.get("kiwi"));
        purchase.handle(transaction2);
        assertEquals(30, Storage.get("kiwi"));
    }

    @Test
    void handle_purchaseAllFromStore_Ok() {
        Storage.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 10);
        purchase.handle(transaction);
        assertEquals(0, Storage.get("apple"));
    }

    @Test
    void handle_purchaseFromZeroStorage_NotOk() {
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", 10);
        assertThrows(RuntimeException.class, () -> purchase.handle(transaction));
    }

    @Test
    void handle_purchaseMoreThenIsInStorage_NotOk() {
        Storage.put("banana", 5);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", 10);
        assertThrows(RuntimeException.class, () -> purchase.handle(transaction));
    }
}
