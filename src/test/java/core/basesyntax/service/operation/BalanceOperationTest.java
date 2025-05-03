package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static BalanceOperation balanceOperation;

    @BeforeAll
    static void beforeAll() {
        balanceOperation = new BalanceOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void operate_validData_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                OperationType.BALANCE, "banana", 20);
        assertDoesNotThrow(() -> balanceOperation.operate(fruitTransaction));
        int actual = Storage.STORAGE.get("banana");
        assertEquals(20, actual);
    }

    @Test
    void operate_withExistedFruit_notOk() {
        Storage.STORAGE.put("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction(
                OperationType.BALANCE, "banana", 100);
        assertThrows(RuntimeException.class, () -> balanceOperation.operate(fruitTransaction),
                "Balance for 'banana' is already set");
    }
}
