package core.basesyntax.service.operation;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class BalanceHandlerTest {
    private static final String FRUIT_NAME = "apple";
    private static final int QUANTITY = 123;
    private static Map<String, Integer> fruitQuantityMap = new HashMap<>();
    private static final OperationHandler BALANCE_HANDLER = new BalanceHandler();

    @Test
    public void checkApply_Ok() {
        BALANCE_HANDLER.apply(fruitQuantityMap, FRUIT_NAME, QUANTITY);
        Assert.assertTrue(fruitQuantityMap.containsKey(FRUIT_NAME)
                && fruitQuantityMap.containsValue(QUANTITY));
    }
}
