package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TransactionTest {
    private static final String FRUIT_APPLE = "apple";
    private static final Integer AMOUNT = 10;
    private Transaction transaction = new Transaction(Operation.BALANCE, FRUIT_APPLE, AMOUNT);

    @Test
    public void getFruit_validValue_ok() {
        assertEquals(FRUIT_APPLE, transaction.getFruit());
    }

    @Test
    public void getAmount_validValue_ok() {
        assertEquals((long) AMOUNT, (long) transaction.getAmount());
    }

    @Test
    public void getOperation_validValue_ok() {
        assertEquals(Operation.BALANCE, transaction.getOperation());
    }
}
