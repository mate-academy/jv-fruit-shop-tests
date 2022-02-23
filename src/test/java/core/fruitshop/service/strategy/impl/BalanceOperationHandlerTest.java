package core.fruitshop.service.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.fruitshop.dao.FruitDao;
import core.fruitshop.dao.FruitDaoImpl;
import core.fruitshop.db.Storage;
import core.fruitshop.model.Fruit;
import core.fruitshop.service.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final String QUANTITY = "75";
    private static final Map<Fruit, Integer> expectedMap = new HashMap<>();
    private static final FruitDao dao = new FruitDaoImpl();
    private final OperationHandler balanceOperationHandler = new BalanceOperationHandler(dao);

    @Test
    public void doOperation_validData_ok() {
        String fruit = APPLE;
        String quantity = QUANTITY;
        balanceOperationHandler.doOperation(fruit, quantity);
        expectedMap.put(new Fruit(fruit), Integer.parseInt(quantity));
        assertEquals(expectedMap, Storage.fruitsStorage);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_quantityLowerZero_notOk() {
        String fruit = APPLE;
        String quantity = "-" + QUANTITY;
        balanceOperationHandler.doOperation(fruit, quantity);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_nullQuantity_notOk() {
        String fruit = APPLE;
        String quantity = null;
        balanceOperationHandler.doOperation(fruit, quantity);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_fruitNull_notOk() {
        String fruit = null;
        String quantity = QUANTITY;
        balanceOperationHandler.doOperation(fruit, quantity);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }
}