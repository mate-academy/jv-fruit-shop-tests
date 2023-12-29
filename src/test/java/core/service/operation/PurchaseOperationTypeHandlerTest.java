package core.service.operation;

import core.model.FruitRecord;
import core.model.OperationType;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationTypeHandlerTest {

    private OperationTypeHandler purchaseHandler;
    private Map<String, Integer> mapFruit = new HashMap<>();

    @Before
    public void setUp() {
        purchaseHandler = new PurchaseOperationTypeHandler();
    }

    @Test
    public void getUpdateAmount_Ok() {
        FruitRecord apple = new FruitRecord("p", "apple", 20);
        mapFruit.put("apple", 100);
        int oldAmount = 100;
        int expected = 80;
        int actual = purchaseHandler.getUpdateAmount(apple, oldAmount);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getUpdateAmount_NotOk() {
        FruitRecord apple = new FruitRecord("p", "apple", 20);
        mapFruit.put("apple", 100);
        int expected = 20;
        int oldAmount = 100;
        int actual = purchaseHandler.getUpdateAmount(apple, oldAmount);
        Assert.assertNotEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getUpdateAmountWithNotEnoughData_NotOk() {
        FruitRecord apple = new FruitRecord("p", "apple", 1000);
        mapFruit.put("apple", 100);
        int oldAmount = 100;
        purchaseHandler.getUpdateAmount(apple, oldAmount);
    }

    @Test(expected = RuntimeException.class)
    public void getUpdateAmountWithNull_NotOk() {
        FruitRecord apple = null;
        mapFruit.put("apple", 100);
        int oldAmount = 120;
        int expected = 20;
        int actual = purchaseHandler.getUpdateAmount(apple, oldAmount);
        Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void getOperationType_NotOk() {
        String operationType = "s";
        Assert.assertNotEquals(OperationType.SUPPLY, purchaseHandler
                .getOperationType(operationType));
    }

    @Test
    public void getOperationType_Ok() {
        String operationType = "p";
        Assert.assertEquals(OperationType.PURCHASE, purchaseHandler
                .getOperationType(operationType));
    }
}
