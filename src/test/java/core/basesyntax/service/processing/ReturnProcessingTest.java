package core.basesyntax.service.processing;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnProcessingTest {
    private static OperationProcessing returnProcessing;
    private static FruitsDao fruitsDao;
    private Map<String, Integer> fruitsAtStorage;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitsDao = new FruitsDaoImpl();
        returnProcessing = new ReturnProcessing(fruitsDao);
    }

    @Before
    public void setUp() throws Exception {
        fruitsAtStorage = new HashMap<>();
        fruitsAtStorage.put("banana", 20);
        fruitsDao.add("banana", 10);
    }

    @Test(expected = RuntimeException.class)
    public void doAction_fruitValueIsNull_notOk() {
        returnProcessing.doAction(null, 200);
    }

    @Test(expected = RuntimeException.class)
    public void doAction_amountIsLessThanZero_notOk() {
        returnProcessing.doAction("banana", -5);
    }

    @Test
    public void doAction_normalValues_ok() {
        returnProcessing.doAction("banana", 10);
        Map<String, Integer> actual = fruitsDao.getFruitsAndQuantityAsMap();
        Map<String, Integer> expected = fruitsAtStorage;
        Assert.assertEquals(expected, actual);
    }
}
