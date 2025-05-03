package core.basesyntax.services;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.Transaction;
import java.util.NoSuchElementException;
import org.junit.Test;

public class OperationHandlerTest {

    @Test
    public void findOperation_Ok() {
        Transaction.Operation expected = Transaction.Operation.BALANCE;
        Transaction.Operation actual = OperationHandler.findOperation("b");
        assertEquals(expected, actual);
    }

    @Test(expected = NoSuchElementException.class)
    public void findOperation_IncorrectOperation_NotOk() {
        OperationHandler.findOperation("z");
    }
}
