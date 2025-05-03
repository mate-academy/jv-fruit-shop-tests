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

public class ReturnOperationTest {
    private ReturnOperation returnOperation;

    @BeforeEach
    public void setUp() {
        returnOperation = new ReturnOperation();
    }

    @Test
    public void validTransaction_updatesFruitStorage_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 2);
        returnOperation.operate(fruitTransaction);
        Map<String, Integer> expectedFruitStorage = new HashMap<>();
        expectedFruitStorage.put("banana", 2);
        assertEquals(expectedFruitStorage, Storage.fruitStorage);
    }

    @Test
    public void operate_negativeQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", -3);
        assertThrows(RuntimeException.class, () -> {
            returnOperation.operate(fruitTransaction);
        });
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }
}
