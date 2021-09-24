package core.service.operation;

import core.model.FruitRecord;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationTypeHandlerTest {
    private OperationTypeHandler returnHandler;
    private Map<String, Integer> mapFruit = new HashMap<>();

    @Before
    public void setUp() {
        returnHandler = new ReturnOperationTypeHandler();
    }

    @Test
    public void getUpdateAmount_Ok() {
        FruitRecord apple = new FruitRecord("r", "apple", 20);
        mapFruit.put("apple", 100);
        int expected = 120;
        int actual = returnHandler.getUpdateAmount(apple, 100);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getUpdateAmount_NotOk() {
        FruitRecord apple = new FruitRecord("r", "apple", 20);
        mapFruit.put("apple", 100);
        int expected = 100;
        int actual = returnHandler.getUpdateAmount(apple, 100);
        Assert.assertNotEquals(expected, actual);
    }
}
