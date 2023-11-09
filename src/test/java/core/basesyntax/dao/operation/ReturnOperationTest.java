package core.basesyntax.dao.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ReturnOperationTest {

    @Test
    void useOperation() {
        // Arrange
        ReturnOperation returnOperation = new ReturnOperation();
        Integer quantity = 10;

        // Act
        Integer result = returnOperation.useOperation(quantity);

        // Assert
        assertEquals(quantity, result, "useOperation should return the same quantity");
    }
}
