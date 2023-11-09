package core.basesyntax.dao.operation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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