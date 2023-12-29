package core.service.operation;

import core.model.FruitRecord;
import core.model.OperationType;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private OperationTypeHandler supplyHandler;
    private Map<String, Integer> mapFruit = new HashMap<>();

    @Before
    public void setUp() {
        supplyHandler = new SupplyOperationHandler();
    }

    @Test
    public void getUpdateAmount_Ok() {
        FruitRecord apple = new FruitRecord("s", "apple", 20);
        mapFruit.put("apple", 100);
        int oldAmount = 100;
        int expected = 120;
        int actual = supplyHandler.getUpdateAmount(apple, oldAmount);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getUpdateAmount_NotOk() {
        FruitRecord apple = new FruitRecord("s", "apple", 20);
        mapFruit.put("apple", 100);
        int oldAmount = 10;
        int expected = 120;
        int actual = supplyHandler.getUpdateAmount(apple, oldAmount);
        Assert.assertNotEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getUpdateAmountWithNull_NotOk() {
        FruitRecord apple = null;
        mapFruit.put("apple", 100);
        int oldAmount = 10;
        int expected = 120;
        int actual = supplyHandler.getUpdateAmount(apple, oldAmount);
        Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void getOperationType_Ok() {
        String operationType = "s";
        Assert.assertEquals(OperationType.SUPPLY, supplyHandler.getOperationType(operationType));
    }

    @Test
    public void getOperationType_NotOk() {
        String operationType = "s";
        Assert.assertNotEquals(OperationType.BALANCE, supplyHandler
                .getOperationType(operationType));
    }
}
