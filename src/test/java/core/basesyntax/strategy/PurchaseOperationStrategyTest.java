package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.fruitstorge.FruitStorage;
import core.basesyntax.fruittransaction.FruitTransaction;
import core.basesyntax.operations.Operation;
import org.junit.jupiter.api.Test;

class PurchaseOperationStrategyTest {
    @Test
    public void testHandleValidTransaction() {
        // Set up initial data in FruitStorage for testing
        OperationHandler operationHandler = new PurchaseOperationStrategy();
        FruitStorage.fruitStorage.put("Apple", 30);
        // Create a FruitTransaction with a valid amount
        FruitTransaction validTransaction = new FruitTransaction(Operation.PURCHASE,"Apple", 5);
        FruitTransaction validTransaction2 = new FruitTransaction(Operation.PURCHASE,"Apple", 10);
        // Call the handle method
        operationHandler.handle(validTransaction);
        operationHandler.handle(validTransaction2);
        // Verify that the balance is updated correctly
        assertEquals(15, FruitStorage.fruitStorage.get("Apple"));
    }

    @Test
    public void testHandleNegativeAmount() {
        // Create a FruitTransaction with a negative amount
        FruitTransaction invalidTransaction = new FruitTransaction(Operation.PURCHASE,"Banana", -3);
        // Use assertThrows to verify that a RuntimeException is thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            new PurchaseOperationStrategy().handle(invalidTransaction);
        });
        // Verify the exception message
        assertEquals("Balance is negative.", exception.getMessage());
        // Verify that FruitStorage is not modified
        //assertNull(FruitStorage.fruitStorage.get("Banana"));
    }
}
