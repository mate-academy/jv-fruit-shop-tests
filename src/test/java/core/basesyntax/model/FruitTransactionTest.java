package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.Operation;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final Operation VALID_OPERATION = Operation.BALANCE;
    private static final String VALID_FRUIT = "apple";
    private static final int VALID_QUANTITY = 20;
    private static final int INVALID_QUANTITY = -20;

    @Test
    void checkConstructorOperationIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            new FruitTransaction(null, VALID_FRUIT, VALID_QUANTITY);
        });
    }

    @Test
    void checkConstructorFruitIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            new FruitTransaction(VALID_OPERATION, null, VALID_QUANTITY);
        });
    }

    @Test
    void checkConstructorQuantityLessThanZero_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            new FruitTransaction(VALID_OPERATION, VALID_FRUIT, INVALID_QUANTITY);
        });
    }

    @Test
    void checkConstructorWithValidData_Ok() {
        assertDoesNotThrow(() -> {
            new FruitTransaction(VALID_OPERATION, VALID_FRUIT, VALID_QUANTITY);
        });
    }
}
