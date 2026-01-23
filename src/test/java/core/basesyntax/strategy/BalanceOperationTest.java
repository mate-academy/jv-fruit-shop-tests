package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.HashMap;
import java.util.Map;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private OperationHandler operationHandler;
    private Map<String, Integer> storage;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        operationHandler = new BalanceOperation();
        storage = new HashMap<>();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void getBalance_validTransaction_ok() {
        storage.put("apple", 50);

        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(50);

        operationHandler.handle(fruitTransaction, storage);

        assertEquals(50, storage.get("apple"));
    }

    @Test
    void handle_negativeBalance_throwException() {
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(-50);

        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(fruitTransaction, storage);
        });
    }

    @Test
    void handle_nullTransaction_throwException() {
        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(null, storage);
        });
    }

    @Test
    void handle_nullStorage_throwException() {
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(100);

        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(fruitTransaction, null);
        });
    }

    @Test
    void handle_nullFruit_throwException() {
        fruitTransaction.setFruit(null);
        fruitTransaction.setQuantity(100);

        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(fruitTransaction, storage);
        });
    }

    @Test
    void handle_emptyFruit_throwException() {
        fruitTransaction.setFruit("");
        fruitTransaction.setQuantity(100);

        assertThrows(RuntimeException.class, () -> {
            operationHandler.handle(fruitTransaction, storage);
        });
    }

}
