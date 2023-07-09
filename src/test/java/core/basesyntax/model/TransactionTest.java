package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TransactionTest {
    private String fruit = "apple";
    private Integer amount = 10;
    private Transaction transaction = new Transaction(Operation.BALANCE, fruit,amount);

    @Test
    public void getFruit_validValue_ok() {
        assertEquals(fruit, transaction.getFruit());
    }

    @Test
    public void getAmount_validValue_ok() {
        assertEquals((long) amount, (long) transaction.getAmount());
    }

    @Test
    public void getOperation_validValue_ok() {
        assertEquals(Operation.BALANCE, transaction.getOperation());
    }
}
