package core.basesyntax.service.processing;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseProcessingTest {
    private static OperationProcessing purchaseProcessing;
    private static FruitsDao fruitsDao;
    private Map<String, Integer> mapWithFruitsAtStorage;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitsDao = new FruitsDaoImpl();
        purchaseProcessing = new PurchaseProcessing(fruitsDao);
    }

    @Before
    public void setUp() throws Exception {
        mapWithFruitsAtStorage = new HashMap<>();
        mapWithFruitsAtStorage.put("banana", 20);
        fruitsDao.add("banana", 30);
    }

    @Test(expected = RuntimeException.class)
    public void doAction_fruitValueIsNull_notOk() {
        purchaseProcessing.doAction(null, 200);
    }

    @Test(expected = RuntimeException.class)
    public void doAction_amountIsLessThanZero_notOk() {
        purchaseProcessing.doAction("banana", -5);
    }

    @Test
    public void doAction_normalValues_ok() {
        purchaseProcessing.doAction("banana", 10);
        Map<String, Integer> actual = fruitsDao.getFruitsAndQuantityAsMap();
        Map<String, Integer> expected = mapWithFruitsAtStorage;
        Assert.assertEquals(expected, actual);
    }
}
