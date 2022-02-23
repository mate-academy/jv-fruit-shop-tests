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

public class SupplyOperationHandlerTest {
    private static final Map<Fruit, Integer> expectedMap = new HashMap<>();
    private static final FruitDao dao = new FruitDaoImpl();
    private static final String APPLE = "apple";
    private static final String QUANTITY = "75";
    private final OperationHandler supplyOperationHandler = new SupplyOperationHandler(dao);
    private final Map<Fruit, Integer> fruitsStorage = Storage.fruitsStorage;

    @Test
    public void doOperation_validData_ok() {
        String fruit = APPLE;
        String quantity = QUANTITY;
        Fruit fruitOne = new Fruit(fruit);
        fruitsStorage.put(fruitOne, 0);
        expectedMap.put(fruitOne, Integer.parseInt(quantity) + fruitsStorage.get(fruitOne));
        supplyOperationHandler.doOperation(fruit, quantity);
        assertEquals(expectedMap, fruitsStorage);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_quantityLowerZero_notOk() {
        String fruit = APPLE;
        String quantity = "-" + QUANTITY;
        supplyOperationHandler.doOperation(fruit, quantity);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_nullQuantity_notOk() {
        String fruit = APPLE;
        String quantity = null;
        supplyOperationHandler.doOperation(fruit, quantity);
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_fruitNull_notOk() {
        String fruit = null;
        String quantity = QUANTITY;
        supplyOperationHandler.doOperation(fruit, quantity);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }

}
