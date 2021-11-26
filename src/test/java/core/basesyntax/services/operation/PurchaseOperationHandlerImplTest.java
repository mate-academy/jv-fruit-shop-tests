package core.basesyntax.services.operation;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.OperationException;
import java.util.Map;
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
        testStorage.clear();
    }

    @Test(expected = OperationException.class)
    public void apply_PurchaseMinusResult_notOk() {
        testStorage.put("apple", 100);
        operationHandler.apply("apple", 120);
        testStorage.clear();
    }

    @Test
    public void apply_PurchaseKeyIsNull_ok() {
        testStorage.put(null, 10);
        operationHandler.apply(null, 10);
        testStorage.clear();
    }
}
