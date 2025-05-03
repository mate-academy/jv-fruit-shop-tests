package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class FruitTransactionTest {
    private static final String FRUIT_NAME = "orange";
    private static final Integer FRUIT_QUANTITY = 25;
    private static final Operation BALANCE = Operation.BALANCE;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setName(FRUIT_NAME);
        fruitTransaction.setQuantity(FRUIT_QUANTITY);
        fruitTransaction.setOperation(BALANCE);
    }

    @Test
    public void getName_isOk() {
        String actual = fruitTransaction.getName();
        assertEquals(FRUIT_NAME, actual);
    }

    @Test
    public void getQuantity_isOk() {
        Integer actual = fruitTransaction.getQuantity();
        assertEquals(FRUIT_QUANTITY, actual);
    }

    @Test
    public void getOperation_isOk() {
        Operation actual = fruitTransaction.getOperation();
        assertEquals(Operation.BALANCE, actual);
    }
}
