package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.LineInformation;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandlerPurchase;

    @BeforeClass
    public static void setUp() {
        operationHandlerPurchase = new PurchaseOperationHandler();
    }

    @Test
    public void operate_CorrectAmount_ok() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 50);
        operationHandlerPurchase.operate(new LineInformation(
                "p", apple, 5));
        Integer expected = 45;
        assertEquals(expected, Storage.storage.get(apple));
    }

    @Test
    public void operate_FewPurchases_ok() {
        Fruit banana = new Fruit("banana");
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 90);
        Storage.storage.put(banana, 110);
        operationHandlerPurchase.operate(new LineInformation(
                "p", banana, 3));
        operationHandlerPurchase.operate(new LineInformation(
                "p", apple, 7));
        operationHandlerPurchase.operate(new LineInformation(
                "p", apple, 15));
        assertEquals(68, (int) Storage.storage.get(apple));
        assertEquals(107, (int) Storage.storage.get(banana));
    }

    @Test (expected = RuntimeException.class)
    public void operate_purchaseNotEnough_notOk() {
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 50);
        operationHandlerPurchase.operate(new LineInformation("p",
                banana, 100));
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}
