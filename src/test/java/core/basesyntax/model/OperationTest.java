package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class OperationTest {

    @Test
    public void balanceFromCodeReturnsOperationBalance() {
        Operation result = Operation.fromCode("b");
        assertEquals(Operation.BALANCE, result);
    }

    @Test
    public void supplyFromCodeReturnsOperationSupply() {
        Operation result = Operation.fromCode("s");
        assertEquals(Operation.SUPPLY, result);
    }

    @Test
    public void purchaseFromCodeReturnsOperationPurchase() {
        Operation result = Operation.fromCode("p");
        assertEquals(Operation.PURCHASE, result);
    }

    @Test
    public void returnFromCodeReturnsOperationReturn() {
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
