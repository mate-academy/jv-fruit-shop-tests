package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static OperationHandler balanceOperationHandler;

    @BeforeAll
    static void beforeAll() {
        balanceOperationHandler = new BalanceOperation();
    }

    @Test
    void operate_nullTransaction_notOk() {
        String expectedMassage = "Transaction can not be null";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> balanceOperationHandler.operate(null));
        assertEquals(expectedMassage, exception.getMessage());
    }

    @Test
    void operate_negativeQuantity_notOk() {
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setFruit("apple");
        balanceTransaction.setQuantity(-10);
        String expectedMessage = "Quantity can't be negative " + balanceTransaction.getQuantity();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> balanceOperationHandler.operate(balanceTransaction));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void operate_positiveQuantity_ok() {
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setFruit("banana");
        balanceTransaction.setQuantity(50);
        int expectedBalanceQuantity = 50;
        balanceOperationHandler.operate(balanceTransaction);
        assertEquals(expectedBalanceQuantity, Storage.fruits.get("banana"));
    }

    @Test
    void operate_zeroQuantity_ok() {
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        balanceTransaction.setFruit("mango");
        balanceTransaction.setQuantity(0);
        int expectedBalanceQuantity = 0;
        balanceOperationHandler.operate(balanceTransaction);
        assertEquals(expectedBalanceQuantity, Storage.fruits.get("mango"));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
