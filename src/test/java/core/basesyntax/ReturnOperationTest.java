package core.basesyntax;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.ReturnOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    @Test
    void returnExistingFruits_Ok() {
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        balanceOperationHandler.processOperation("Banana", 10);
        balanceOperationHandler.processOperation("Apple", 20);
        balanceOperationHandler.processOperation("Grape", 3);
        OperationHandler returnOperationHandler = new ReturnOperationHandler();
        returnOperationHandler.processOperation("Banana", 5);
        returnOperationHandler.processOperation("Apple", 17);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("Banana", 15);
        expectedStorage.put("Apple", 37);
        expectedStorage.put("Grape", 3);
        Assertions.assertEquals(expectedStorage, new StorageDaoImpl().getStock());
    }

    @Test
    void returnNonExistingFruits_NotOk() {
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        balanceOperationHandler.processOperation("Banana", 10);
        balanceOperationHandler.processOperation("Apple", 20);
        balanceOperationHandler.processOperation("Grape", 3);
        OperationHandler returnOperationHandler = new ReturnOperationHandler();
        Assertions.assertThrows(RuntimeException.class,
                () -> returnOperationHandler.processOperation("Orange", 5));
    }

    @AfterEach
    void tearDown() {
        Storage.STOCK.clear();
    }
}
