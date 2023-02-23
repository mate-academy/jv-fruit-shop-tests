package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class FruitTransactionTest {
    private static final FruitTransaction.Operation OPERATION =
            FruitTransaction.Operation.SUPPLY;
    private static final String INVALID_OPERATION = "i";
    private static final String FRUIT = "banana";
    private static final int QUANTITY = 50;

    private static FruitTransaction fruitTransaction;

    @Test
    public void createFruitTransaction_Ok() {
        //arrange
        fruitTransaction = new FruitTransaction(OPERATION, FRUIT, QUANTITY);

        //assert
        assertEquals("Incorrect operation: ",
                OPERATION, fruitTransaction.getOperation());
        assertEquals("Incorrect fruit: ",
                FRUIT, fruitTransaction.getFruit());
        assertEquals("Incorrect quantity: ",
                QUANTITY, fruitTransaction.getQuantity());
    }

    @Test(expected = RuntimeException.class)
    public void createWithInvalidOperation_NotOk() {
        //arrange
        fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.getByCode(INVALID_OPERATION),
                FRUIT,
                QUANTITY);

        //assert
        fail("Non-existent operation is not allowed.");
    }
}
