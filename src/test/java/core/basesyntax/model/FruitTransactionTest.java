package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String expectedFruit = "apple";
    private static final String expectedOperation = "b";
    private static final int expectedQuantity = 12;

    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        fruitTransaction = new FruitTransaction(expectedOperation, expectedFruit, expectedQuantity);
    }

    @Test
    void getFruitValidData() {
        assertEquals(expectedFruit, fruitTransaction.getFruit());
    }

    @Test
    void getQuantityValidData() {
        assertEquals(expectedQuantity, fruitTransaction.getQuantity());
    }

    @Test
    void getOperationValidData() {
        FruitTransaction.Operation operation =
                FruitTransaction.Operation.fromCode(expectedOperation);
        assertEquals(operation, fruitTransaction.getOperation());
    }
}
