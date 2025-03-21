package core.basesyntax.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class OperationStrategyImplTest {
    @Test
    void testGetOperationValidCode() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.getOperation("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.getOperation("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.getOperation("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.getOperation("r"));
    }

    @Test
    void testGetOperationInvalidCode() {
        // Test with an invalid code to ensure IllegalArgumentException is thrown
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.getOperation("invalid"));
    }
  
}