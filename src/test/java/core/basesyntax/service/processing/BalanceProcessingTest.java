package core.basesyntax.service.processing;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceProcessingTest {
    private static OperationProcessing balanceProcessing;
    private static FruitsDao fruitsDao;
    private static Map<String, Integer> fruitsAtStorageMap;

    @BeforeClass
    public static void beforeClass() {
        fruitsAtStorageMap = new HashMap<>();
        fruitsDao = new FruitsDaoImpl(fruitsAtStorageMap);
        balanceProcessing = new BalanceProcessing(fruitsDao);
    }

    @Before
    public void setUp() {
        fruitsAtStorageMap.put("banana", 10);
    }

    @Test(expected = RuntimeException.class)
    public void doAction_inputValueOfFruitIsNull_notOk() {
        balanceProcessing.doAction(null, 200);
    }

    @Test(expected = RuntimeException.class)
    public void doAction_amountIsLessThanZero_notOk() {
        balanceProcessing.doAction("banana", -5);
    }

    @Test
    public void doAction_normalValues_ok() {
        balanceProcessing.doAction("banana", 10);
        Map<String, Integer> actual = fruitsDao.getFruitsAndQuantityAsMap();
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        Assert.assertEquals(expected, actual);
    }
}
