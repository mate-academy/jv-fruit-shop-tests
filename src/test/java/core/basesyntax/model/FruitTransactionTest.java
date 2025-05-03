package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String APPLE = "apple";
    private static final int INITIAL_FRUIT_QUANTITY = 5;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void setAndGetAllOperations_Ok() {
        fruitTransaction.setOperation(Operation.SUPPLY);
        assertEquals(Operation.SUPPLY, fruitTransaction.getOperation(),
                "Operation should be SUPPLY");

        fruitTransaction.setOperation(Operation.BALANCE);
        assertEquals(Operation.BALANCE, fruitTransaction.getOperation(),
                "Operation should be BALANCE");

        fruitTransaction.setOperation(Operation.PURCHASE);
        assertEquals(Operation.PURCHASE, fruitTransaction.getOperation(),
                "Operation should be PURCHASE");

        fruitTransaction.setOperation(Operation.RETURN);
        assertEquals(Operation.RETURN, fruitTransaction.getOperation(),
                "Operation should be RETURN");
    }

    @Test
    void setAndGetFruit_Ok() {
        fruitTransaction.setFruit(APPLE);
        assertEquals(APPLE, fruitTransaction.getFruit(),
                "Fruit should be an apple");
    }

    @Test
    void setAndGetQuantity_Ok() {
        fruitTransaction.setQuantity(INITIAL_FRUIT_QUANTITY);
        assertEquals(INITIAL_FRUIT_QUANTITY, fruitTransaction.getQuantity(),
                "Quantity should be 5");
    }
}

