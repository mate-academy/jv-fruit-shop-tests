package core.basesyntax.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private SupplyOperationHandler supplyOperationHandler;
    private Map<String, Integer> expectedMap;
    private FruitTransaction fruitTransaction = new FruitTransaction(
            FruitTransaction.Operation.SUPPLY,
            "banana",
            20
    );

    @BeforeEach
    void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
        expectedMap = new HashMap<>();
        expectedMap.put("banana", 120); // expected balance after supply operation

        Storage.fruitStorage.clear();
        Storage.fruitStorage.put("banana", 100); // balance before supply operation
    }

    @Test
    void get_validInput_ok() {
        supplyOperationHandler.get(fruitTransaction);
        assertEquals(expectedMap, Storage.fruitStorage);
    }

    @Test
    void get_nullInput_throwsRuntimeException() {
        assertThrows(RuntimeException.class,
                () -> supplyOperationHandler.get(null));
    }

    @Test
    void get_addFruitWithoutBalanceOperationBefore_throwsIllegalArgumentException() {
        Storage.fruitStorage.clear();
        assertThrows(IllegalArgumentException.class,
                () -> supplyOperationHandler.get(fruitTransaction));
    }
}
