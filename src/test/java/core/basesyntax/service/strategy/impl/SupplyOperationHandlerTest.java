package core.basesyntax.service.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final Map<Fruit, Integer> expectedMap = new HashMap<>();
    private final OperationHandler supplyOperationHandler = new SupplyOperationHandler();
    private final Map<Fruit, Integer> fruitsStorage = Storage.fruitsStorage;

    @Test
    public void doOperation_validData_ok() {
        String fruit = "apple";
        String quantity = "175";
        Fruit fruitOne = new Fruit(fruit);
        fruitsStorage.put(fruitOne, 0);
        expectedMap.put(fruitOne, Integer.parseInt(quantity) + fruitsStorage.get(fruitOne));
        supplyOperationHandler.doOperation(fruit, quantity);
        assertEquals(expectedMap, fruitsStorage);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_quantityLowerZero_notOk() {
        String fruit = "apple";
        String quantity = "-75";
        supplyOperationHandler.doOperation(fruit, quantity);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_nullQuantity_notOk() {
        String fruit = "apple";
        String quantity = null;
        supplyOperationHandler.doOperation(fruit, quantity);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_fruitNull_notOk() {
        String fruit = null;
        String quantity = "23";
        supplyOperationHandler.doOperation(fruit, quantity);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }
}
