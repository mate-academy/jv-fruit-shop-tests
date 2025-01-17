package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void constructor_validArguments_fieldsInitialized() {
        OperationType operation = OperationType.BALANCE;
        String fruit = "apple";
        int quantity = 100;
        FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
        assertEquals(operation, transaction.getOperation(), "Operation should match");
        assertEquals(fruit, transaction.getFruit(), "Fruit should match");
        assertEquals(quantity, transaction.getQuantity(), "Quantity should match");
    }

    @Test
    void settersAndGetters_validUpdates_valuesUpdated() {
        FruitTransaction transaction = new FruitTransaction(OperationType.BALANCE, "apple", 100);
        transaction.setOperation(OperationType.PURCHASE);
        assertEquals(OperationType.PURCHASE, transaction.getOperation(),
                "Operation should be updated");
        transaction.setFruit("banana");
        assertEquals("banana", transaction.getFruit(), "Fruit should be updated");
        transaction.setQuantity(200);
        assertEquals(200, transaction.getQuantity(), "Quantity should be updated");
    }
}
