package core.basesyntax.operationservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationImplTest {
    private BalanceOperationImpl balanceOperation;
    private Map<String, Integer> storage;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        balanceOperation = new BalanceOperationImpl();
    }

    @Test
    void apply_existingFruit_updatesBalance() {
        storage.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 100);
        balanceOperation.apply(transaction);
        assertEquals(100, Storage.getQuantity("banana"));
    }

    @Test
    void apply_negativeQuantity_throwsException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", -50);
        assertThrows(IllegalArgumentException.class, () -> balanceOperation.apply(transaction));
    }
}
