package core.basesyntax.service.operation;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseHandlerTest {
    public static final String FRUIT_NAME = "apple";
    public static final int QUANTITY = 100;
    public static final int DIFFERENCE = 1;
    private static final OperationHandler HANDLER = new PurchaseHandler();

    @Test
    public void checkApply_Ok() {
        Map<String, Integer> fruitQuantityMap = new HashMap<>();
        fruitQuantityMap.put(FRUIT_NAME, QUANTITY);
        HANDLER.apply(fruitQuantityMap, FRUIT_NAME, DIFFERENCE);
        int expected = QUANTITY - DIFFERENCE;
        int actual = fruitQuantityMap.get(FRUIT_NAME);
        Assert.assertEquals(expected, actual);
    }
}
