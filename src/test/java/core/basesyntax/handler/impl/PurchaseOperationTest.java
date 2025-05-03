package core.basesyntax.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;
    private Map<String, Integer> fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new HashMap<>();
        purchaseOperation = new PurchaseOperation(fruitStorage);
    }

    @Test
    void apply_validTransaction_ok() {
        fruitStorage.put("banana", 100);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(30);
        purchaseOperation.apply(transaction);
        assertEquals(70, fruitStorage.get("banana"));
    }

    @Test
    void apply_insufficientQuantity_notOk() {
        fruitStorage.put("banana", 20);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(30);
        purchaseOperation.apply(transaction);
        assertEquals(-10, fruitStorage.get("banana"));
    }
}
