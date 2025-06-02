package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.handler.OperationHandlerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationHandlerImplTest {
    private OperationHandlerImpl handler;

    @BeforeEach
    void setUp() {
        handler = new OperationHandlerImpl();
        Storage.fruitStorage.clear();
    }

    @Test
    void handle_unsupportedOperation_shouldThrowException() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(5);
        transaction.setOperation(FruitTransaction.Operation.UNKNOWN);

        UnsupportedOperationException exception = Assertions.assertThrows(
                UnsupportedOperationException.class,
                () -> handler.handle(transaction)
        );

        Assertions.assertTrue(exception.getMessage().contains("Unsupported operation"));
    }

    @Test
    void handle_shouldThrowIfOperationNull() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(null);
        RuntimeException ex = assertThrows(UnsupportedOperationException.class,
                () -> handler.handle(transaction));
        assertEquals("Operation is null", ex.getMessage());
    }

    @Test
    void handle_balance_shouldSetQuantity() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("apple");
        transaction.setQuantity(10);

        handler.handle(transaction);

        assertEquals(10, Storage.fruitStorage.get("apple"));
    }

    @Test
    void handle_supply_shouldAddQuantity() {
        Storage.fruitStorage.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("apple");
        transaction.setQuantity(7);

        handler.handle(transaction);

        assertEquals(12, Storage.fruitStorage.get("apple"));
    }

    @Test
    void handle_return_shouldAddQuantity() {
        Storage.fruitStorage.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("apple");
        transaction.setQuantity(3);

        handler.handle(transaction);

        assertEquals(8, Storage.fruitStorage.get("apple"));
    }

    @Test
    void handle_purchase_shouldSubtractQuantity() {
        Storage.fruitStorage.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("apple");
        transaction.setQuantity(7);

        handler.handle(transaction);

        assertEquals(3, Storage.fruitStorage.get("apple"));
    }

    @Test
    void handle_purchase_shouldThrowIfNotEnoughStock() {
        Storage.fruitStorage.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("apple");
        transaction.setQuantity(10);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> handler.handle(transaction));
        assertTrue(ex.getMessage().contains("Not enough stock"));
    }

    @Test
    void handle_shouldSetBalanceQuantity() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("apple");
        transaction.setQuantity(100);

        handler.handle(transaction);

        assertEquals(100, Storage.fruitStorage.get("apple"));
    }

    @Test
    void handle_shouldAddQuantityOnSupply() {
        Storage.fruitStorage.put("banana", 50);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("banana");
        transaction.setQuantity(30);

        handler.handle(transaction);

        assertEquals(80, Storage.fruitStorage.get("banana"));
    }

    @Test
    void handle_shouldAddQuantityOnReturn() {
        Storage.fruitStorage.put("orange", 40);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("orange");
        transaction.setQuantity(10);

        handler.handle(transaction);

        assertEquals(50, Storage.fruitStorage.get("orange"));
    }

    @Test
    void handle_shouldSubtractQuantityOnPurchase() {
        Storage.fruitStorage.put("grape", 70);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("grape");
        transaction.setQuantity(20);

        handler.handle(transaction);

        assertEquals(50, Storage.fruitStorage.get("grape"));
    }

    @Test
    void handle_shouldThrowException_whenPurchaseQuantityExceedsStock() {
        Storage.fruitStorage.put("kiwi", 10);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("kiwi");
        transaction.setQuantity(15);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> handler.handle(transaction));

        assertEquals("Not enough stock for purchase of kiwi", exception.getMessage());
    }

    @Test
    void handle_shouldThrowException_whenOperationIsNull() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("pear");
        transaction.setQuantity(10);
        transaction.setOperation(null);

        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> handler.handle(transaction));

        assertEquals("Operation is null", exception.getMessage());
    }
}
