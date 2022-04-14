package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;
import org.junit.Test;

public class OperationTypeTest {

    @Test
    public void getOperation_Ok() {
        assertEquals(OperationType.BALANCE, OperationType.getOperation("b"));
        assertEquals(OperationType.SUPPLY, OperationType.getOperation("s"));
        assertEquals(OperationType.PURCHASE, OperationType.getOperation("p"));
        assertEquals(OperationType.RETURN, OperationType.getOperation("r"));
    }

    @Test(expected = NoSuchElementException.class)
    public void getOperation_throwException_notOk() {
        OperationType.getOperation("a");
    }
}
