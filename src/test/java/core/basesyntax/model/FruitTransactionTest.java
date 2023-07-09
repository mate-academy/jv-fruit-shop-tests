package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String FRUIT_NAME = "apple";
    private static final int QUANTITY = 10;

    @Test
    void getOperation_ValidOperation_Success() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);

        assertEquals(FruitTransaction.Operation.PURCHASE, fruitTransaction.getOperation());
    }

    @Test
    void setOperation_InvalidOperation_ThrowsException() {
        FruitTransaction fruitTransaction = new FruitTransaction();

        assertThrows(RuntimeException.class, () ->
                fruitTransaction.setOperation(FruitTransaction.Operation.getOption("invalid")));
    }

    @Test
    void getFruit_ValidFruit_Success() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(FRUIT_NAME);

        assertEquals(FRUIT_NAME, fruitTransaction.getFruit());
    }

    @Test
    void setFruit_ValidFruit_Success() {
        FruitTransaction fruitTransaction = new FruitTransaction();

        fruitTransaction.setFruit(FRUIT_NAME);

        assertEquals(FRUIT_NAME, fruitTransaction.getFruit());
    }

    @Test
    void getQuantity_ValidQuantity_Success() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(QUANTITY);

        assertEquals(QUANTITY, fruitTransaction.getQuantity());
    }

    @Test
    void setQuantity_ValidQuantity_Success() {
        FruitTransaction fruitTransaction = new FruitTransaction();

        fruitTransaction.setQuantity(QUANTITY);

        assertEquals(QUANTITY, fruitTransaction.getQuantity());
    }
}
