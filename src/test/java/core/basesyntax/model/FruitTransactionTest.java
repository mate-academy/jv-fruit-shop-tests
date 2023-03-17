package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String DEFAULT_OPERATION = "b";
    private static final String DEFAULT_FRUIT_NAME = "banana";
    private static final int DEFAULT_QUANTITY = 100;
    private static final String INCORRECT_OPERATION = "d";
    private static final int INCORRECT_QUANTITY = -1;
    private static FruitTransaction fruitTransaction;
    private static FruitTransaction.Operation operation;

    @BeforeAll
    static void beforeAll() {
        fruitTransaction = new FruitTransaction(DEFAULT_OPERATION,
                DEFAULT_FRUIT_NAME, DEFAULT_QUANTITY);
    }

    @Test
    void getOperation_default_ok() {
        assertEquals(fruitTransaction.getOperation().getCode(), DEFAULT_OPERATION);
    }

    @Test
    void getFruitName_default_ok() {
        assertEquals(fruitTransaction.getFruit(), DEFAULT_FRUIT_NAME);
    }

    @Test
    void getQuantity_default_ok() {
        assertEquals(fruitTransaction.getQuantity(), DEFAULT_QUANTITY);
    }

    @Test
    void constructorFilledWithNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            new FruitTransaction(null, null, null);
        });
    }

    @Test
    void notRegisteredConstructorArgument_notOk() {
        assertThrows(RuntimeException.class, () -> {
            new FruitTransaction(INCORRECT_OPERATION, DEFAULT_FRUIT_NAME, DEFAULT_QUANTITY);
        });
    }

    @Test
    void negativeQuantityArgument_notOk() {
        assertThrows(RuntimeException.class, () -> {
            new FruitTransaction(DEFAULT_OPERATION, DEFAULT_FRUIT_NAME, INCORRECT_QUANTITY);
        });
    }
}
