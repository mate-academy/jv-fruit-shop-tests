package core.basesyntax.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OperationTest {

    @Test
    public void bFromCodeReturnsOperationBalance() {
        Operation result = Operation.fromCode("b");
        assertEquals(Operation.BALANCE, result);
    }

    @Test
    public void sFromCodeReturnsOperationSupply() {
        Operation result = Operation.fromCode("s");
        assertEquals(Operation.SUPPLY, result);
    }

    @Test
    public void pFromCodeReturnsOperationPurchase() {
        Operation result = Operation.fromCode("p");
        assertEquals(Operation.PURCHASE, result);
    }

    @Test
    public void rFromCodeReturnsOperationReturn() {
        Operation result = Operation.fromCode("r");
        assertEquals(Operation.RETURN, result);
    }

    @Test
    public void nonExistentFromCodeReturnsException() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> Operation.fromCode("x")
        );
        assertEquals("Unknown operation: x", exception.getMessage());
    }
}
