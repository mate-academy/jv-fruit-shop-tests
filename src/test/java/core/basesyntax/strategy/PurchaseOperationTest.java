package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        operationHandler = new PurchaseOperation();
        fruitTransaction = new FruitTransaction();
        storage = new HashMap<>();
    }

    @Test
    void handle_validTransaction_ok() {
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(100);

        storage.put("apple", 120);

        operationHandler.handle(fruitTransaction, storage);

        assertEquals(20, storage.get("apple"));
    }

    @Test
    void handle_nullTransaction_throwException() {
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(null, storage);
        });
    }

    @Test
    void handle_nullStorage_throwException() {
        fruitTransaction.setQuantity(100);
        fruitTransaction.setFruit("apple");

        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(fruitTransaction, null);
        });
    }

    @Test
    void handle_nullFruit_throwException() {
        fruitTransaction.setQuantity(100);
        fruitTransaction.setFruit(null);

        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(fruitTransaction, storage);
        });
    }

    @Test
    void handle_emptyFruit_throwException() {
        fruitTransaction.setQuantity(100);
        fruitTransaction.setFruit("");

        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(fruitTransaction, storage);
        });
    }

    @Test
    void handle_negativeQuantity_throwException() {
        fruitTransaction.setQuantity(-10);
        fruitTransaction.setFruit("apple");

        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(fruitTransaction, storage);
        });
    }

    @Test
    void handle_buyMoreThanExists_throwException() {
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(100);

        storage.put("apple", 50);

        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(fruitTransaction, storage);
        });
    }

}
