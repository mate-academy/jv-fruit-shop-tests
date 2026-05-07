package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static OperationHandler supplyOperationHandler;

    @BeforeAll
    static void beforeAll() {
        supplyOperationHandler = new SupplyOperation();
    }

    @Test
    void operate_nullTransaction_notOk() {
        String expectedMessage = "Transaction can not be null";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> supplyOperationHandler.operate(null));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void operate_negativeQuantity_notOk() {
        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyTransaction.setFruit("mango");
        supplyTransaction.setQuantity(-10);
        String expectedMessage = "Quantity can't be negative "
                + supplyTransaction.getQuantity();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> supplyOperationHandler.operate(supplyTransaction));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void operate_nonExistentFruit_notOk() {
        Storage.fruits.put("apple", 10);
        Storage.fruits.put("banana", 50);
        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyTransaction.setFruit("mango");
        supplyTransaction.setQuantity(100);
        String expectedMessage = "There is no such fruit in storage: "
                + supplyTransaction.getFruit();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> supplyOperationHandler.operate(supplyTransaction));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void operate_positiveQuantity_ok() {
        Storage.fruits.put("apple", 10);
        Storage.fruits.put("banana", 50);
        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyTransaction.setFruit("apple");
        supplyTransaction.setQuantity(100);
        int expectedAppleQuantity = 110;
        supplyOperationHandler.operate(supplyTransaction);
        assertEquals(expectedAppleQuantity, Storage.fruits.get("apple"));
    }

    @Test
    void operate_zeroBalancePositiveSupply_ok() {
        Storage.fruits.put("apple", 10);
        Storage.fruits.put("banana", 0);
        FruitTransaction supplyTransaction = new FruitTransaction();
        supplyTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        supplyTransaction.setFruit("banana");
        supplyTransaction.setQuantity(200);
        int expectedBananaQuantity = 200;
        supplyOperationHandler.operate(supplyTransaction);
        assertEquals(expectedBananaQuantity, Storage.fruits.get("banana"));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
