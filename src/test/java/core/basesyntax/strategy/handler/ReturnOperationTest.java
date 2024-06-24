package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final String BANANA = "banana";
    private static final int DEFAULT_QUANTITY = 10;
    private static final int ZERO_QUANTITY = 0;
    private static final int NEGATIVE_QUANTITY = - 1;

    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new ReturnOperation();
    }

    @AfterEach
    void afterEach() {
        Storage.clear();
    }

    @Test
    void process_returnDefaultQuantity_ok() {
        Storage.addFruit(BANANA, ZERO_QUANTITY);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, BANANA, DEFAULT_QUANTITY
        );
        operationHandler.process(fruitTransaction);
        int actualQuantity = Storage.getFruits().getOrDefault(BANANA, ZERO_QUANTITY);
        assertEquals(DEFAULT_QUANTITY, actualQuantity);
    }

    @Test
    void process_returnNegativeQuantity_notOk() {
        Storage.addFruit(BANANA, DEFAULT_QUANTITY);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, BANANA, NEGATIVE_QUANTITY
        );
        assertThrows(IllegalArgumentException.class,
                () -> operationHandler.process(fruitTransaction));
    }

    @Test
    void process_returnZeroQuantity_notOk() {
        Storage.addFruit(BANANA, DEFAULT_QUANTITY);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, BANANA, ZERO_QUANTITY
        );
        assertThrows(IllegalArgumentException.class,
                () -> operationHandler.process(fruitTransaction));
    }
}
