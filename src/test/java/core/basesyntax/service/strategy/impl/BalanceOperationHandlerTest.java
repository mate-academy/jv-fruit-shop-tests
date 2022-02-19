package core.basesyntax.service.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final Map<Fruit, Integer> expectedMap = new HashMap<>();
    private final OperationHandler balanceOperationHandler = new BalanceOperationHandler();

    @Test
    public void doOperation_validData_ok() {
        String fruit = "apple";
        String quantity = "75";
        balanceOperationHandler.doOperation(fruit, quantity);
        expectedMap.put(new Fruit(fruit), Integer.parseInt(quantity));
        assertEquals(expectedMap, Storage.fruitsStorage);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_quantityLowerZero_notOk() {
        String fruit = "apple";
        String quantity = "-75";
        balanceOperationHandler.doOperation(fruit, quantity);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_nullQuantity_notOk() {
        String fruit = "apple";
        String quantity = null;
        balanceOperationHandler.doOperation(fruit, quantity);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_fruitNull_notOk() {
        String fruit = null;
        String quantity = "23";
        balanceOperationHandler.doOperation(fruit, quantity);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }
}
