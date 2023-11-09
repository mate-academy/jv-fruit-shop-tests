package core.basesyntax.dao.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SupplyOperationTest {

    @Test
    void useOperation_Ok() {
        // Arrange
        SupplyOperation supplyOperation = new SupplyOperation();
        Integer quantity = 10;

        // Act
        Integer result = supplyOperation.useOperation(quantity);

        // Assert
        assertEquals(quantity, result, "useOperation should return the same quantity");
    }
}
