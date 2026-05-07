package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static OperationHandler returnOperationHandler;

    @BeforeAll
    static void beforeAll() {
        returnOperationHandler = new ReturnOperation();
    }

    @Test
    void operate_nullTransaction_notOk() {
        String expectedMessage = "Transaction can not be null";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> returnOperationHandler.operate(null));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void operate_negativeQuantity_notOk() {
        FruitTransaction returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnTransaction.setFruit("apple");
        returnTransaction.setQuantity(-20);
        String expectedMessage = "Quantity can't be negative "
                + returnTransaction.getQuantity();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> returnOperationHandler.operate(returnTransaction));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void operate_nonExistentFruit_notOk() {
        Storage.fruits.put("apple", 100);
        Storage.fruits.put("banana", 50);
        FruitTransaction returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnTransaction.setFruit("mango");
        returnTransaction.setQuantity(20);
        String expectedMessage = "There is no such fruit in storage: "
                + returnTransaction.getFruit();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> returnOperationHandler.operate(returnTransaction));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void operate_positiveQuantity_ok() {
        Storage.fruits.put("apple", 100);
        Storage.fruits.put("banana", 50);
        FruitTransaction returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnTransaction.setFruit("apple");
        returnTransaction.setQuantity(20);
        returnOperationHandler.operate(returnTransaction);
        int expectedQuantity = 120;
        assertEquals(expectedQuantity, Storage.fruits.get("apple"));
    }

    @Test
    void operate_zeroBalanceReturn_ok() {
        Storage.fruits.put("apple", 0);
        Storage.fruits.put("banana", 50);
        FruitTransaction returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnTransaction.setFruit("apple");
        returnTransaction.setQuantity(100);
        returnOperationHandler.operate(returnTransaction);
        int expectedQuantity = 100;
        assertEquals(expectedQuantity, Storage.fruits.get("apple"));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
