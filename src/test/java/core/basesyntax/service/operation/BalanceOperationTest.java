package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static FruitTransaction transaction;
    private static Map<String, Integer> expectedStorage;
    private static BalanceOperation balanceOperation;

    @BeforeAll
    static void beforeAll() {
        expectedStorage = new HashMap<>();
        balanceOperation = new BalanceOperation();
    }

    @AfterEach
    void afterEach() {
        expectedStorage.clear();
    }

    @Test
    void updateStorage_validDataInput_ok() {
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "cocos", 35);
        expectedStorage.put("cocos", 35);
        balanceOperation.updateStorage(transaction);
        Map<String, Integer> actual = FruitStorage.fruitStorage;
        assertEquals(expectedStorage, actual);
    }

    @Test
    void updateStorage_negativeQuantityInput_notOk() {
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "cocos", -35);
        assertThrows(RuntimeException.class, () -> balanceOperation.updateStorage(transaction));
    }
}
