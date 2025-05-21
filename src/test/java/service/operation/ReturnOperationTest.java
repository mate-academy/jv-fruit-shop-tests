package service.operation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private final OperationHandler operationHandler = new ReturnOperation();

    @Test
    void testReturnOperation_ok() {
        Storage.add("strawberry", 5);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("strawberry");
        fruitTransaction.setQuantity(30);
        assertDoesNotThrow(() -> operationHandler.performOperation(fruitTransaction));
        assertEquals(35, Storage.getAmount("strawberry"));
    }

    @Test
    void testNegativeReturnOperation_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        assertThrows(IllegalArgumentException.class, () -> fruitTransaction.setQuantity(-10));
    }

    @Test
    void testZeroReturnOperation_ok() {
        Storage.add("apple", 0);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(0);
        assertDoesNotThrow(() -> operationHandler.performOperation(fruitTransaction));
        assertEquals(0, Storage.getAmount("apple"));
    }
}
