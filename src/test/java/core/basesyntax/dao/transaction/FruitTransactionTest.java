package core.basesyntax.dao.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.operation.Operation;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void testFruitTransactionConstructor_Ok() {
        // Arrange
        Operation operation = Operation.BALANCE;
        String name = "apple";
        Integer quantity = 10;

        // Act
        FruitTransaction fruitTransaction = new FruitTransaction(operation, name, quantity);

        // Assert
        assertEquals(operation, fruitTransaction.getOperation());
        assertEquals(name, fruitTransaction.getName());
        assertEquals(quantity, fruitTransaction.getQuantity());
    }
}
