package core.basesyntax;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.ReturnOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private static OperationHandler returnOperationHandler;

    @BeforeAll
    static void beforeAll() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @Test
    void returnExistingFruits_ReturnOperation_Ok() {
        Storage.STOCK.put("Banana", 10);
        Storage.STOCK.put("Apple", 20);
        Storage.STOCK.put("Grape", 3);

        returnOperationHandler.processOperation("Banana", 5);
        returnOperationHandler.processOperation("Apple", 17);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("Banana", 15);
        expectedStorage.put("Apple", 37);
        expectedStorage.put("Grape", 3);
        Assertions.assertEquals(expectedStorage, new StorageDaoImpl().getStock());
    }

    @Test
    void returnNonExistingFruits_ReturnOperation_NotOk() {
        Storage.STOCK.put("Banana", 10);
        Storage.STOCK.put("Apple", 20);
        Storage.STOCK.put("Grape", 3);

        Assertions.assertThrows(RuntimeException.class,
                () -> returnOperationHandler.processOperation("Orange", 5));
    }

    @Test
    void returnNonNullFruits_ReturnOperation_NotOk() {
        Storage.STOCK.put("Banana", 10);
        Storage.STOCK.put("Apple", 20);
        Storage.STOCK.put("Grape", 3);

        Assertions.assertThrows(RuntimeException.class,
                () -> returnOperationHandler.processOperation(null, null));
    }

    @AfterEach
    void tearDown() {
        Storage.STOCK.clear();
    }
}
