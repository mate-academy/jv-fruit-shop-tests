package core.fruitshop.service.strategy.impl;

import static org.junit.Assert.*;

import core.fruitshop.dao.FruitDao;
import core.fruitshop.dao.FruitDaoImpl;
import core.fruitshop.db.Storage;
import core.fruitshop.model.Fruit;
import core.fruitshop.service.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final String QUANTITY = "75";
    public static final FruitDao dao = new FruitDaoImpl();
    private static final Map<Fruit, Integer> expectedMap = new HashMap<>();
    private final OperationHandler purchaseOperationHandler = new PurchaseOperationHandler(dao);
    private final Map<Fruit, Integer> fruitsStorage = Storage.fruitsStorage;

    @Test
    public void doOperation_validData_ok() {
        String fruit = APPLE;
        String quantity = QUANTITY;
        Fruit appleFruit = new Fruit(APPLE);
        fruitsStorage.put(appleFruit, 100);
        expectedMap.put(new Fruit(fruit), fruitsStorage.get(appleFruit)
            - Integer.parseInt(quantity));
        purchaseOperationHandler.doOperation(fruit, quantity);
        assertEquals(expectedMap, fruitsStorage);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_balanceBecameLowerZero_notOk() {
        String fruit = APPLE;
        String quantity = QUANTITY;
        fruitsStorage.put(new Fruit(fruit), Integer.parseInt(quantity));
        purchaseOperationHandler.doOperation(fruit, "120");
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_quantityLowerZero_notOk() {
        String fruit = APPLE;
        String quantity = "-" + QUANTITY;
        purchaseOperationHandler.doOperation(fruit, quantity);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_nullQuantity_notOk() {
        String fruit = APPLE;
        String quantity = null;
        purchaseOperationHandler.doOperation(fruit, quantity);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_fruitNull_notOk() {
        String fruit = null;
        String quantity = QUANTITY;
        purchaseOperationHandler.doOperation(fruit, quantity);
    }

    @After
    public void tearDown() {
        fruitsStorage.clear();
    }

}