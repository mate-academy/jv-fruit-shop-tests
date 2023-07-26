package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Operation;
import org.junit.jupiter.api.Test;

class OperationTest {
    @Test
    public void testCheckTypeOperationPurchase() {
        String typeOperation = "p";
        Operation operation = Operation.checkTypeOperation(typeOperation);
        assertEquals(Operation.PURCHASE, operation);
    }

    @Test
    public void testCheckTypeOperationSupply() {
        String typeOperation = "s";
        Operation operation = Operation.checkTypeOperation(typeOperation);
        assertEquals(Operation.SUPPLY, operation);
    }

    @Test
    public void testCheckTypeOperationBalance() {
        String typeOperation = "b";
        Operation operation = Operation.checkTypeOperation(typeOperation);
        assertEquals(Operation.BALANCE, operation);
    }

    @Test
    public void testCheckTypeOperationReturn() {
        String typeOperation = "r";
        Operation operation = Operation.checkTypeOperation(typeOperation);
        assertEquals(Operation.RETURN, operation);
    }

    @Test
    public void testCheckTypeOperationInvalidInput() {
        String typeOperation = "invalid";
        assertThrows(RuntimeException.class, () -> Operation.checkTypeOperation(typeOperation));
    }
}
