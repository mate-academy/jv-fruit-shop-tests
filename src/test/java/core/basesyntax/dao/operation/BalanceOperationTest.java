package core.basesyntax.dao.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
