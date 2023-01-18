package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TransactionTest {
    private static Transaction transaction;
    private static Operation operation;

    @Before
    public void setUp() {
        operation = new Operation("BALANCE", "b", Operation.ArithmeticOperation.ADD);
        transaction = new Transaction("apple", 2);
    }

    @Test
    public void getFruit_Ok() {
        transaction = new Transaction("banana", 10);
        assertEquals(transaction.getFruit(), "banana");
    }

    @Test
    public void getQuantity_Ok() {
        transaction.setQuantity(100);
        assertEquals(transaction.getQuantity(), 100);
    }

    @Test
    public void setOperation_Ok() {
        transaction.setOperation(operation);
        assertEquals(transaction.getOperation(), operation);
    }
}
