package core.basesyntax.strategy.handlerimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    public void setUp() {
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    public void validTransaction_updatesFruitStorage_ok() {
        Storage.fruitStorage.put("banana", 10);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 4);
        Map<String, Integer> initialFruitStorage = new HashMap<>(Storage.fruitStorage);
        purchaseOperation.operate(fruitTransaction);
        int expectedQuantity = initialFruitStorage.getOrDefault("banana", 0) - 4;
        assertEquals(expectedQuantity, Storage.fruitStorage.getOrDefault("banana", 0));
    }

    @Test
    public void operate_negativeQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", -15);
        assertThrows(RuntimeException.class, () -> {
            purchaseOperation.operate(fruitTransaction);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }
}
