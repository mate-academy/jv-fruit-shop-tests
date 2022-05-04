package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.LineInformation;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandlerPurchase;
    private static Map<Fruit, Integer> storage;
    private static Fruit banana;
    private static Fruit apple;

    @BeforeClass
    public static void setUp() {
        operationHandlerPurchase = new PurchaseOperationHandler();
        storage = Storage.storage;
        banana = new Fruit("banana");
        apple = new Fruit("apple");
    }

    @Test
    public void operate_CorrectAmount_ok() {
        storage.put(apple, 50);
        operationHandlerPurchase.operate(new LineInformation(
                "p", apple, 5));
        Integer expected = 45;
        assertEquals(expected, storage.get(apple));
    }

    @Test
    public void operate_FewPurchases_ok() {
        storage.put(apple, 90);
        storage.put(banana, 110);
        operationHandlerPurchase.operate(new LineInformation(
                "p", banana, 3));
        operationHandlerPurchase.operate(new LineInformation(
                "p", apple, 7));
        operationHandlerPurchase.operate(new LineInformation(
                "p", apple, 15));
        assertEquals(68, (int) storage.get(apple));
        assertEquals(107, (int) storage.get(banana));
    }

    @Test (expected = RuntimeException.class)
    public void operate_purchaseNotEnough_notOk() {
        storage.put(banana, 50);
        operationHandlerPurchase.operate(new LineInformation("p",
                banana, 100));
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}
