package core.basesyntax;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    @Test
    void supply100Banana_Ok() {
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        balanceOperationHandler.processOperation("Banana", 10);
        balanceOperationHandler.processOperation("Apple", 20);
        balanceOperationHandler.processOperation("Grape", 3);

        OperationHandler supplyOperationHandler = new SupplyOperationHandler();
        supplyOperationHandler.processOperation("Banana", 100);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("Banana", 110);
        expectedStorage.put("Apple", 20);
        expectedStorage.put("Grape", 3);
        Assertions.assertEquals(expectedStorage, new StorageDaoImpl().getStock());
    }

    @Test
    void supplySomeFruitsWithIncorrectValue_NotOk() {
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        Assertions.assertThrows(RuntimeException.class,
                () -> balanceOperationHandler.processOperation("Banana", -10));
    }

    @Test
    void supplyNonExistingFruit_NotOk() {
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        balanceOperationHandler.processOperation("Banana", 10);
        balanceOperationHandler.processOperation("Apple", 20);
        balanceOperationHandler.processOperation("Grape", 3);
        OperationHandler supplyOperationHandler = new SupplyOperationHandler();
        Assertions.assertThrows(RuntimeException.class,
                () -> supplyOperationHandler.processOperation("Orange", 100));
    }

    @AfterEach
    void tearDown() {
        Storage.STOCK.clear();
    }
}
