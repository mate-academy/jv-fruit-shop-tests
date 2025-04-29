package core.basesyntax.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {

    private BalanceOperationHandler balanceOperationHandler;

    private Map<String, Integer> expectedMap;

    @BeforeEach
    void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
        expectedMap = new HashMap<>();
        Storage.fruitStorage.clear();
    }

    @Test
    void get_nullInput_throwsRunTimeExceptions() {
        assertThrows(RuntimeException.class,
                () -> balanceOperationHandler.get(null));
    }

    @Test
    void get_setBalanceInStorage_ok() {
        expectedMap.put("banana", 10);

        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                10
        );

        balanceOperationHandler.get(fruitTransaction);

        assertEquals(expectedMap, Storage.fruitStorage);
    }
}
