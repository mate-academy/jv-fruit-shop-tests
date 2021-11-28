package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.OperationException;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerImplTest {
    private static OperationHandler operationHandler;
    private static Map<String, Integer> testStorage;

    @BeforeClass
    public static void beforeClass() {
        testStorage = Storage.storage;
        operationHandler = new PurchaseOperationHandlerImpl(new FruitStorageDaoImpl());
    }

    @Test(expected = OperationException.class)
    public void apply_resultLowerThenZero_notOk() {
        testStorage.put("apple", 100);
        operationHandler.apply("apple", 120);
    }

    @Test
    public void apply_keyIsNull_ok() {
        testStorage.put(null, 10);
        operationHandler.apply(null, 10);
        Integer expected = 0;
        Integer actual = testStorage.get(null);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_purchaseApplicable_ok() {
        testStorage.put("banana", 150);
        operationHandler.apply("banana", 120);
        Integer expected = 30;
        Integer actual = testStorage.get("banana");
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        testStorage.clear();
    }
}
