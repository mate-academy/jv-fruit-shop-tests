package core.basesyntax.service.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final Map<Fruit, Integer> expectedMap = new HashMap<>();
    private final OperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
    private final Map<Fruit, Integer> fruitsStorage = Storage.fruitsStorage;

    @Test
    public void doOperation_validData_ok() {
        String fruit = "apple";
        String quantity = "75";
        Fruit appleFruit = new Fruit("apple");
        fruitsStorage.put(appleFruit, 100);
        expectedMap.put(new Fruit(fruit), fruitsStorage.get(appleFruit)
                - Integer.parseInt(quantity));
        purchaseOperationHandler.doOperation(fruit, quantity);
        assertEquals(expectedMap, fruitsStorage);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_balanceBecameLowerZero_notOk() {
        String fruit = "apple";
        String quantity = "100";
        fruitsStorage.put(new Fruit(fruit), Integer.parseInt(quantity));
        purchaseOperationHandler.doOperation(fruit, "120");
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_quantityLowerZero_notOk() {
        String fruit = "apple";
        String quantity = "-75";
        purchaseOperationHandler.doOperation(fruit, quantity);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_nullQuantity_notOk() {
        String fruit = "apple";
        String quantity = null;
        purchaseOperationHandler.doOperation(fruit, quantity);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_fruitNull_notOk() {
        String fruit = null;
        String quantity = "23";
        purchaseOperationHandler.doOperation(fruit, quantity);
    }

    @After
    public void tearDown() {
        fruitsStorage.clear();
    }
}
