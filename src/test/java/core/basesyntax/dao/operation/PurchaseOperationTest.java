package core.basesyntax.dao.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PurchaseOperationTest {

    @Test
    void useOperation() {
        // Arrange
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        Integer quantity = 10;

        // Act
        Integer result = purchaseOperation.useOperation(quantity);

        // Assert
        assertEquals(-10, result, "useOperation should return the negative of the quantity");
    }
}
