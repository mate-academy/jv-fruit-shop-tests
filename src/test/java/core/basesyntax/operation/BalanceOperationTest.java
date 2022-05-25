package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.operation.BalanceOperation;
import core.basesyntax.strategy.operation.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceOperation();
    }

    @After
    public void after() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void handle_NotSameFruit_Ok() {
        Map<String, Integer> expectedDB = new HashMap<>();
        expectedDB.put("banana", 20);
        expectedDB.put("apple", 1);
        operationHandler.handle("banana", 20);
        operationHandler.handle("apple", 1);
        Assert.assertTrue(expectedDB.entrySet()
                .containsAll(Storage.fruitStorage.entrySet()));
        Assert.assertEquals(expectedDB.size(),Storage.fruitStorage.size());
    }

    @Test
    public void handle_SameFruit_Ok() {
        Map<String, Integer> expectedDB = new HashMap<>();
        expectedDB.put("banana", 21);
        operationHandler.handle("banana", 20);
        operationHandler.handle("banana", 1);
        Assert.assertTrue(expectedDB.entrySet()
                .containsAll(Storage.fruitStorage.entrySet()));
        Assert.assertEquals(expectedDB.size(),Storage.fruitStorage.size());
    }
}
