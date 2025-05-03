package core.service.operation;

import core.model.FruitRecord;
import core.model.OperationType;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationTypeHandlerTest {
    private OperationTypeHandler balanceHandler;
    private Map<String, Integer> mapFruit = new HashMap<>();

    @Before
    public void setUp() {
        balanceHandler = new BalanceOperationTypeHandler();
    }

    @Test
    public void getUpdateAmount_Ok() {
        FruitRecord apple = new FruitRecord("b", "apple", 20);
        mapFruit.put("apple", 100);
        int expected = 100;
        int actual = balanceHandler.getUpdateAmount(apple, 100);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getUpdateAmount_NotOk() {
        FruitRecord apple = new FruitRecord("b", "apple", 20);
        mapFruit.put("apple", 100);
        int expected = 20;
        int actual = balanceHandler.getUpdateAmount(apple, 100);
        Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void getOperationType_Ok() {
        String operationType = "b";
        Assert.assertEquals(OperationType.BALANCE, balanceHandler.getOperationType(operationType));
    }

    @Test
    public void getOperationType_NotOk() {
        String operationType = "s";
        Assert.assertNotEquals(OperationType.SUPPLY, balanceHandler
                .getOperationType(operationType));
    }
}
