package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int DEFAULT_QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -1;
    private static final int QUANTITY = 5;
    private static final int ZERO_QUANTITY = 0;
    private static OperationHandler handler;

    @BeforeAll
    static void beforeAll() {
        handler = new ReturnOperationHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.fruits.clear();
    }

    @Test
    void returnHandle_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () -> handler.process(new FruitTransaction(
                FruitTransaction.Operation.RETURN, BANANA, NEGATIVE_QUANTITY)));
    }

    @Test
    void returnHandle_emptyFruitStorage_ok() {
        Storage.fruits.put(BANANA, DEFAULT_QUANTITY);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, APPLE, DEFAULT_QUANTITY);
        handler.process(fruitTransaction);

        Integer actualQuantity = Storage.fruits.getOrDefault(APPLE, ZERO_QUANTITY);
        assertEquals(DEFAULT_QUANTITY, actualQuantity);
    }

    @Test
    void returnHandle_notEmptyFruitStorage_ok() {
        Storage.fruits.put(BANANA, DEFAULT_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, BANANA, QUANTITY);
        handler.process(transaction);
        int expectedBananaQuantity = DEFAULT_QUANTITY + QUANTITY;
        Integer actualBananaQuantity = Storage.fruits.getOrDefault(BANANA, ZERO_QUANTITY);
        assertEquals(expectedBananaQuantity, actualBananaQuantity);
    }

    @Test
    void returnHandle_addToExistingFruitStorageNegativeQuantity_ok() {
        Storage.fruits.put(BANANA, NEGATIVE_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, BANANA, DEFAULT_QUANTITY);
        handler.process(transaction);

        int expectedBananaQuantity = NEGATIVE_QUANTITY + DEFAULT_QUANTITY;
        Integer actualBananaQuantity = Storage.fruits.getOrDefault(BANANA, ZERO_QUANTITY);
        assertEquals(expectedBananaQuantity, actualBananaQuantity);
    }
}
