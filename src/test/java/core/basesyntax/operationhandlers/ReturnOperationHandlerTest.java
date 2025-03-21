package core.basesyntax.operationhandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransactionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private ReturnOperationHandler handler;
    private FruitTransactionImpl transaction;

    @BeforeEach
    void setUp() {
        handler = new ReturnOperationHandler();
        transaction = new FruitTransactionImpl();
        Storage.fruits.clear();
    }

    @Test
    void apply_AddNewFruit_Ok() {
        transaction.setFruit("apple");
        transaction.setQuantity(50);

        handler.apply(transaction);

        assertEquals(50, Storage.fruits.get("apple"),
                "The quantity of apples should be 50");
    }

    @Test
    void apply_UpdateExistingFruit_Ok() {
        Storage.addFruit("banana", 30);
        transaction.setFruit("banana");
        transaction.setQuantity(20);

        handler.apply(transaction);

        assertEquals(50, Storage.fruits.get("banana"),
                "The quantity of bananas should be 50");
    }

    @Test
    void apply_NullTransaction_ThrowsException() {
        assertThrows(NullPointerException.class, () -> handler.apply(null),
                "Expected NullPointerException when passing null");
    }

    @Test
    void apply_RemoveFruit_NotEnoughStock_ThrowsException() {
        Storage.addFruit("orange", 5);
        transaction.setFruit("orange");
        transaction.setQuantity(10);

        assertThrows(IllegalArgumentException.class, () -> Storage.removeFruit("orange", 10),
                "Expected IllegalArgumentException if there is not enough stock of the fruit");
    }
}
