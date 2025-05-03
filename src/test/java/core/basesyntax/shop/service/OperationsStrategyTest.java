package core.basesyntax.shop.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.shop.item.Fruit;
import core.basesyntax.shop.service.operations.Balance;
import core.basesyntax.shop.service.operations.OperationHandler;
import core.basesyntax.shop.service.operations.Purchase;
import core.basesyntax.shop.service.operations.Return;
import core.basesyntax.shop.service.operations.Supply;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationsStrategyTest {
    private static final Fruit thirtyBananas = new Fruit("banana", 30);

    @Before
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void testOperationStrategyMapOk() {
        Map<String, OperationHandler> operationMap = new HashMap<>();
        operationMap.put("b", new Balance());
        OperationsStrategy operationsStrategy = new OperationsStrategyImpl(operationMap);
        assertEquals(Balance.class, operationsStrategy.get("b").getClass());
    }

    @Test
    public void testBalanceOk() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationHandler operationHandler = new Balance();
        operationHandler.apply(thirtyBananas);
        Integer expected = 30;
        assertEquals(expected, fruitDao.getAll().get(0).getQuality());
    }

    @Test
    public void testPurchaseOk() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationHandler operationHandler = new Purchase();
        fruitDao.add(thirtyBananas);
        operationHandler.apply(thirtyBananas);
        Integer expected = 0;
        assertEquals(expected, fruitDao.getAll().get(0).getQuality());
    }

    @Test
    public void testReturnOk() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationHandler operationHandler = new Return();
        fruitDao.add(thirtyBananas);
        operationHandler.apply(thirtyBananas);
        Integer expected = 60;
        assertEquals(expected, fruitDao.getAll().get(0).getQuality());
    }

    @Test
    public void testSupplyOk() {
        FruitDao fruitDao = new FruitDaoImpl();
        OperationHandler operationHandler = new Supply();
        fruitDao.add(thirtyBananas);
        operationHandler.apply(thirtyBananas);
        Integer expected = 60;
        assertEquals(expected, fruitDao.getAll().get(0).getQuality());
    }
}
