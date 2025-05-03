package core.basesyntax;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static OperationHandler supplyOperationHandler;

    @BeforeAll
    static void beforeAll() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    void supply100Banana_SupplyOperation_Ok() {
        Storage.STOCK.put("Banana", 10);
        Storage.STOCK.put("Apple", 20);
        Storage.STOCK.put("Grape", 3);

        supplyOperationHandler.processOperation("Banana", 100);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("Banana", 110);
        expectedStorage.put("Apple", 20);
        expectedStorage.put("Grape", 3);
        Assertions.assertEquals(expectedStorage, new StorageDaoImpl().getStock());
    }

    @Test
    void supplySomeFruitsWithIncorrectValue_SupplyOperation_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> supplyOperationHandler.processOperation("Banana", -10));
    }

    @Test
    void supplyNullFruits_SupplyOperation_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> supplyOperationHandler.processOperation(null, null));
    }

    @Test
    void supplyNonExistingFruit_SupplyOperation_NotOk() {
        Storage.STOCK.put("Banana", 10);
        Storage.STOCK.put("Apple", 20);
        Storage.STOCK.put("Grape", 3);

        Assertions.assertThrows(RuntimeException.class,
                () -> supplyOperationHandler.processOperation("Orange", 100));
    }

    @AfterEach
    void tearDown() {
        Storage.STOCK.clear();
    }
}
