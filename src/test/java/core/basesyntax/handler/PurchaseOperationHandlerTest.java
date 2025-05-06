package core.basesyntax.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler purchaseOperationHandler;
    private Map<String, Integer> expectedMap;
    private FruitTransaction fruitTransaction = new FruitTransaction(
            FruitTransaction.Operation.PURCHASE,
            "banana",
            20
    );

    @BeforeEach
    void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        expectedMap = new HashMap<>();
        expectedMap.put("banana", 100); // expected balance after purchase operation

        Storage.fruitStorage.clear();
        Storage.fruitStorage.put("banana", 120); // balance before purchase operation
    }

    @Test
    void get_validInput_ok() {
        purchaseOperationHandler.get(fruitTransaction);
        assertEquals(expectedMap, Storage.fruitStorage);
    }

    @Test
    void get_nullInput_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> purchaseOperationHandler.get(null));
    }

    @Test
    void get_addFruitWithoutBalanceOperationBefore_throwsIllegalArgumentException() {
        Storage.fruitStorage.clear();
        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperationHandler.get(fruitTransaction));
    }
}
