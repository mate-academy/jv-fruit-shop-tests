package core.basesyntax.service.operation;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class ReturnHandlerTest {
    private static final OperationHandler HANDLER = new ReturnHandler();

    @Test
    public void checkApply_Ok() {
        Map<String, Integer> fruitQuantityMap = new HashMap<>();
        fruitQuantityMap.put(PurchaseHandlerTest.FRUIT_NAME,
                PurchaseHandlerTest.QUANTITY);
        HANDLER.apply(fruitQuantityMap,
                PurchaseHandlerTest.FRUIT_NAME,
                PurchaseHandlerTest.DIFFERENCE);
        int actual = fruitQuantityMap.get(PurchaseHandlerTest.FRUIT_NAME);
        int expected = PurchaseHandlerTest.QUANTITY + PurchaseHandlerTest.DIFFERENCE;
        Assert.assertEquals(expected, actual);
    }
}
