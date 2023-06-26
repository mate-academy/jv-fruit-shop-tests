package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private OperationHandler operationHandler = new PurchaseOperationHandler();
    private Map<String, Integer> expected = new HashMap<>();
    private Map<String, Integer> actual = new HashMap<>();

    @BeforeEach
    void setUp() {
        Storage.FRUITS.put("banana", 50);
        Storage.FRUITS.put("apple", 50);
        Storage.FRUITS.put("orange", 50);
    }

    @Test
    void getPurchaseWithBanana_Ok() {
        expected.put("banana", 40);
        expected.put("apple", 50);
        expected.put("orange", 50);
        FruitTransaction banana = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "banana", 10);
        operationHandler.handle(banana);
        actual = Storage.FRUITS;

        assertEquals(expected, actual, "There's an error during processing the operation RETURN");
    }

    @Test
    void getPurchaseWithApple_Ok() {
        expected.put("banana", 50);
        expected.put("apple", 40);
        expected.put("orange", 50);
        FruitTransaction apple = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "apple", 10);
        operationHandler.handle(apple);
        actual = Storage.FRUITS;

        assertEquals(expected, actual, "There's an error during processing the operation RETURN");
    }

    @Test
    void getPurchaseWithOrange_Ok() {
        expected.put("banana", 50);
        expected.put("apple", 50);
        expected.put("orange", 40);
        FruitTransaction orange = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "orange", 10);
        operationHandler.handle(orange);
        actual = Storage.FRUITS;

        assertEquals(expected, actual, "There's an error during processing the operation RETURN");
    }

    @Test
    void getPurchaseWithLargerQuantity_notOk() {
        FruitTransaction banana = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "banana", 100);
        FruitTransaction apple = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "apple", 100);
        FruitTransaction orange = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "orange", 100);

        assertAll(
                () -> assertThrows(RuntimeException.class, () -> operationHandler.handle(banana)),
                () -> assertThrows(RuntimeException.class, () -> operationHandler.handle(apple)),
                () -> assertThrows(RuntimeException.class, () -> operationHandler.handle(orange))
        );

    }

    @Test
    void getPurchaseWithNewFruitOrNull_notOk() {
        FruitTransaction fruitTransactionNull = null;
        FruitTransaction newFruit = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "new fruit", 10);

        assertAll(
                () -> assertThrows(RuntimeException.class, () -> operationHandler.handle(newFruit)),
                () -> assertThrows(RuntimeException.class, () -> operationHandler
                        .handle(fruitTransactionNull))
        );
    }

    @AfterEach
    void clearStorage() {
        Storage.FRUITS.clear();
    }
}
