package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int DEFAULT_QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -1;
    private static final int QUANTITY = 5;
    private static final int ZERO_VALUE = 0;
    private static OperationHandler handler;

    @BeforeAll
    static void beforeAll() {
        handler = new SupplyOperationHandler();
    }

    @AfterEach
    public void afterEach() {
        Storage.fruits.clear();
    }

    @Test
    void supplyHandler_nullFruitTransaction_notOk() {
        assertThrows(NullPointerException.class, () -> handler.process(null));
    }

    @Test
    void supplyHandler_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction transaction = new FruitTransaction(
                    FruitTransaction.Operation.SUPPLY, BANANA, NEGATIVE_QUANTITY);
            handler.process(transaction);
        });
    }

    @Test
    void supplyHandler_emptyFruitStorage_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, APPLE, DEFAULT_QUANTITY);
        handler.process(transaction);
        Integer actualQuantity = Storage.fruits.getOrDefault(APPLE, ZERO_VALUE);
        assertEquals(DEFAULT_QUANTITY, actualQuantity);
    }

    @Test
    void supplyHandler_multipleFruits_ok() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, APPLE, DEFAULT_QUANTITY);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, BANANA, QUANTITY);

        handler.process(transaction1);
        handler.process(transaction2);
        Integer actualAppleQuantity = Storage.fruits.getOrDefault(APPLE, ZERO_VALUE);
        Integer actualBananaQuantity = Storage.fruits.getOrDefault(BANANA, ZERO_VALUE);

        assertEquals(DEFAULT_QUANTITY, actualAppleQuantity);
        assertEquals(QUANTITY, actualBananaQuantity);
    }
}
