package core.basesyntax.dao.operation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplyOperationTest {

    @Test
    void useOperation() {
        // Arrange
        SupplyOperation supplyOperation = new SupplyOperation();
        Integer quantity = 10;

        // Act
        Integer result = supplyOperation.useOperation(quantity);

        // Assert
        assertEquals(quantity, result, "useOperation should return the same quantity");
    }
}