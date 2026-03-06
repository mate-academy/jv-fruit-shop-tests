package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private FruitTransaction fruitTransaction;
    private BalanceOperation balanceOperation;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        storage = Storage.getFruitStorage();
        Storage.clear();
        fruitTransaction = new FruitTransaction();
        balanceOperation = new BalanceOperation();
    }

    @Test
    void performOperation_validData_ok() {
        fruitTransaction.setFruit("apple");
        fruitTransaction.setOperation("b");
        fruitTransaction.setQuantity(10);
        balanceOperation.performOperation(fruitTransaction);
        int expected = 10;
        int actual = storage.get("apple");
        assertEquals(expected, actual);
    }

    @Test
    void performOperation_invalidData_throwException() {
        fruitTransaction.setFruit("apple");
        fruitTransaction.setOperation("b");
        fruitTransaction.setQuantity(-10);
        assertThrows(RuntimeException.class,
                () -> balanceOperation.performOperation(fruitTransaction));
    }
}
