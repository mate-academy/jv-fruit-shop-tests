package core.basesyntax;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    @Test
    void addSomeFruits_Ok() {
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        balanceOperationHandler.processOperation("Banana", 10);
        balanceOperationHandler.processOperation("Apple", 20);
        balanceOperationHandler.processOperation("Grape", 3);

        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("Banana", 10);
        expectedStorage.put("Apple", 20);
        expectedStorage.put("Grape", 3);
        Assertions.assertEquals(expectedStorage, new StorageDaoImpl().getStock());
    }

    @Test
    void addSomeFruitsWithIncorrectValue_NotOk() {
        OperationHandler balanceOperationHandler = new BalanceOperationHandler();
        Assertions.assertThrows(RuntimeException.class,
                () -> balanceOperationHandler.processOperation("Banana", -10));
    }

    @AfterEach
    void tearDown() {
        Storage.STOCK.clear();
    }
}
