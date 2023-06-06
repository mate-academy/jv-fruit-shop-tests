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

public class SupplyOperationTest {
    private SupplyOperation supplyOperation;

    @BeforeEach
    public void setUp() {
        supplyOperation = new SupplyOperation();
    }

    @Test
    public void validTransaction_updatesFruitStorage_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 5);
        supplyOperation.operate(fruitTransaction);
        Map<String, Integer> expectedFruitStorage = new HashMap<>();
        expectedFruitStorage.put("banana", 5);
        assertEquals(expectedFruitStorage, Storage.fruitStorage);
    }

    @Test
    public void operate_negativeQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", -2);
        assertThrows(RuntimeException.class, () -> {
            supplyOperation.operate(fruitTransaction);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }
}
