package core.basesyntax.service.operation;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class SupplyHandlerTest {
    private static final OperationHandler HANDLER = new SupplyHandler();

    @Test
    public void checkApply_Ok() {
        Map<String, Integer> fruitQuantityMap = new HashMap<>();
        fruitQuantityMap.put(PurchaseHandlerTest.FRUIT_NAME,
                PurchaseHandlerTest.QUANTITY);
        HANDLER.apply(fruitQuantityMap,
                PurchaseHandlerTest.FRUIT_NAME,
                PurchaseHandlerTest.DIFFERENCE);
        int expected = PurchaseHandlerTest.QUANTITY + PurchaseHandlerTest.DIFFERENCE;
        int actual = fruitQuantityMap.get(PurchaseHandlerTest.FRUIT_NAME);
        Assert.assertEquals(expected, actual);
    }
}
