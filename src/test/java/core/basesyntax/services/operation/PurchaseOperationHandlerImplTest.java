package core.basesyntax.services.operation;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.OperationException;
import java.util.Map;
import org.junit.After;
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
    public void apply_PurchaseValueIsNull_notOk() {
        testStorage.put("apple", null);
        operationHandler.apply("apple", 10);
    }

    @Test(expected = OperationException.class)
    public void apply_PurchaseMinusResult_notOk() {
        testStorage.put("apple", 100);
        operationHandler.apply("apple", 120);
    }

    @Test
    public void apply_PurchaseKeyIsNull_ok() {
        testStorage.put(null, 10);
        operationHandler.apply(null, 10);
    }

    @After
    public void afterEachTest() {
        testStorage.clear();
    }
}
