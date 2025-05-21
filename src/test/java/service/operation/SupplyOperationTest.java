package service.operation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private final OperationHandler operationHandler = new SupplyOperation();

    @Test
    void testSupplyOperation_ok() {
        Storage.add("apple", 15);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(23);
        assertDoesNotThrow(() -> operationHandler.performOperation(fruitTransaction));
        assertEquals(38, Storage.getAmount("apple"));
    }

    @Test
    void testNegativeSupplyOperation_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        assertThrows(IllegalArgumentException.class, () -> fruitTransaction.setQuantity(-39));
    }

    @Test
    void testZeroSupplyOperation_ok() {
        Storage.add("pineapple", 45);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("pineapple");
        fruitTransaction.setQuantity(0);
        assertDoesNotThrow(() -> operationHandler.performOperation(fruitTransaction));
        assertEquals(45, Storage.getAmount("pineapple"));
    }
}
