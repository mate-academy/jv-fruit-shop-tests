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

class SupplyOperationHandlerTest {
    private OperationHandler operationHandler = new SupplyOperationHandler();
    private Map<String, Integer> expected = new HashMap<>();
    private Map<String, Integer> actual = new HashMap<>();

    @BeforeEach
    void setUp() {
        Storage.FRUITS.put("banana", 50);
        Storage.FRUITS.put("apple", 50);
        Storage.FRUITS.put("orange", 50);
    }

    @Test
    void getSupplyWithBanana_Ok() {
        expected.put("banana", 60);
        expected.put("apple", 50);
        expected.put("orange", 50);
        FruitTransaction banana = new FruitTransaction(FruitTransaction.Operation
                .SUPPLY, "banana", 10);
        operationHandler.handle(banana);
        actual = Storage.FRUITS;

        assertEquals(expected, actual, "There's an error during processing the operation SUPPLY");
    }

    @Test
    void getSupplyWithApple_Ok() {
        expected.put("banana", 50);
        expected.put("apple", 60);
        expected.put("orange", 50);
        FruitTransaction apple = new FruitTransaction(FruitTransaction.Operation
                .SUPPLY, "apple", 10);
        operationHandler.handle(apple);
        actual = Storage.FRUITS;

        assertEquals(expected, actual, "There's an error during processing the operation SUPPLY");
    }

    @Test
    void getSupplyWithOrange_Ok() {
        expected.put("banana", 50);
        expected.put("apple", 50);
        expected.put("orange", 60);
        FruitTransaction orange = new FruitTransaction(FruitTransaction.Operation
                .SUPPLY, "orange", 10);
        operationHandler.handle(orange);
        actual = Storage.FRUITS;

        assertEquals(expected, actual, "There's an error during processing the operation SUPPLY");
    }

    @Test
    void getSupplyWithNewFruitOrNull_notOk() {
        FruitTransaction fruitTransactionNull = null;
        FruitTransaction newFruit = new FruitTransaction(FruitTransaction
                .Operation.SUPPLY, "new fruit", 10);

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
