package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static FruitTransaction transaction;
    private static Map<String, Integer> expectedStorage;
    private static SupplyOperation supplyOperation;

    @BeforeAll
    static void beforeAll() {
        expectedStorage = new HashMap<>();
        supplyOperation = new SupplyOperation();
    }

    @Test
    void updateStorage_validDataInput_ok() {
        FruitStorage.fruitStorage.put("cocos", 100);
        transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "cocos", 35);
        expectedStorage.put("cocos", 135);
        supplyOperation.updateStorage(transaction);
        Map<String, Integer> actual = FruitStorage.fruitStorage;
        assertEquals(expectedStorage, actual);
    }

    @Test
    void updateStorage_negativeQuantityInput_notOk() {
        transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "cocos", -35);
        assertThrows(RuntimeException.class, () -> supplyOperation.updateStorage(transaction));
    }

    @Test
    void updateStorage_nullFruitInput_notOk() {
        transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, null, 35);
        assertThrows(RuntimeException.class, () -> supplyOperation.updateStorage(transaction));
    }
}
