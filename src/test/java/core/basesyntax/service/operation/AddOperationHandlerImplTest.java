package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerImplTest {
    private static OperationHandler operationHandler;
    private static Map<String, Integer> testStorage;

    @BeforeClass
    public static void beforeClass() {
        testStorage = Storage.storage;
        operationHandler = new AddOperationHandlerImpl(new FruitStorageDaoImpl());
    }

    @Test
    public void apply_AddValidData_emptyStorage_ok() {
        operationHandler.apply("banana", 10);
        Integer expected = 10;
        Integer actual = testStorage.get("banana");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_AddValidData_notEmptyStorage_ok() {
        testStorage.put("banana", 200);
        operationHandler.apply("banana", 100);
        Integer expected = 300;
        Integer actual = testStorage.get("banana");
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        testStorage.clear();
    }
}
