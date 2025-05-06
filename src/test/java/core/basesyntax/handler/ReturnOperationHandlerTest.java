package core.basesyntax.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private ReturnOperationHandler returnOperationHandler;
    private Map<String, Integer> expectedMap;
    private FruitTransaction fruitTransaction = new FruitTransaction(
            FruitTransaction.Operation.RETURN,
            "banana",
            20
    );

    @BeforeEach
    void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
        expectedMap = new HashMap<>();
        expectedMap.put("banana", 120); // expected balance after return operation

        Storage.fruitStorage.clear();
        Storage.fruitStorage.put("banana", 100); // balance before return operation
    }

    @Test
    void get_validInput_ok() {
        returnOperationHandler.get(fruitTransaction);
        assertEquals(expectedMap, Storage.fruitStorage);
    }

    @Test
    void get_nullInput_throwsRuntimeException() {
        assertThrows(RuntimeException.class,
                () -> returnOperationHandler.get(null));
    }

    @Test
    void get_addFruitWithoutBalanceOperationBefore_throwsIllegalArgumentException() {
        Storage.fruitStorage.clear();
        assertThrows(IllegalArgumentException.class,
                () -> returnOperationHandler.get(fruitTransaction));
    }
}
