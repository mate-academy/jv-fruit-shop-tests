package core.basesyntax.dao.operation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalanceOperationTest {

    @Test
    void useOperation() {
        // Arrange
        BalanceOperation balanceOperation = new BalanceOperation();
        Integer quantity = 10;

        //Act
        Integer result = balanceOperation.useOperation(quantity);

        // Assert
        assertEquals(quantity, result, "useOperation should return the same quantity");
    }
}