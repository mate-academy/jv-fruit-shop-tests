package core.basesyntax.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private SupplyOperation supplyOperation;
    private Map<String, Integer> fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new HashMap<>();
        supplyOperation = new SupplyOperation(fruitStorage);
    }

    @Test
    void apply_validTransaction_ok() {
        fruitStorage.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(30);
        supplyOperation.apply(transaction);
        assertEquals(80, fruitStorage.get("banana"));
    }
}
