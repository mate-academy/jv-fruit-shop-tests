package core.basesyntax.handler.impl;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BalanceOperationTest {
    private BalanceOperation balanceOperation;
    private Map<String, Integer> fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new HashMap<>();
        balanceOperation = new BalanceOperation(fruitStorage);
    }

    @Test
    void apply_validTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(100);
        balanceOperation.apply(transaction);
        assertEquals(100, fruitStorage.get("banana"));
    }
}
