package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitModel;
import core.basesyntax.strategy.handlers.BalanceOperationHandler;
import org.junit.Test;

public class StrategyImplTest {
    @Test
    public void executeStrategy_strategyWorkingCorrectly_Ok() {
        OperationHandler operationHandler = new BalanceOperationHandler();
        StrategyImpl strategy = new StrategyImpl(operationHandler);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("patata", 13);
        assertTrue(strategy.executeStrategy(fruitModel));
        assertTrue(storageDao.containsKey(fruitModel.getName()));
        assertEquals(storageDao.getAmount(fruitModel.getName()), 13);
    }
}
