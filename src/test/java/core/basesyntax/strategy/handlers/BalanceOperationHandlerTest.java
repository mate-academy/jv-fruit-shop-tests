package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitModel;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.strategy.validator.CommodityValidator;
import org.junit.After;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    @After
    public void tearDown() throws Exception {
        FruitStorage.fruitStorage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void negativeAmount_Exception() {
        CommodityValidator commodityValidator = new BalanceOperationHandler();
        FruitModel fruitModel = new FruitModel("apple", -10);
        commodityValidator.isFruitAmountCorrect(fruitModel, "b");
    }

    @Test
    public void correctData_True() {
        CommodityValidator commodityValidator = new BalanceOperationHandler();
        FruitModel fruitModel = new FruitModel("Kevin_the_minion", 10);
        assertTrue(commodityValidator.isFruitAmountCorrect(fruitModel, "b"));
    }

    @Test(expected = RuntimeException.class)
    public void fruitIsAlreadyExists_Exception() {
        BalanceOperationHandler balanceOperation = new BalanceOperationHandler();
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        storageDao.putFruitModel(fruitModel);
        balanceOperation.doOperation(fruitModel);
    }

    /*
    @Test
    public void fruitIsAdded_True() {
        BalanceOperationHandler balanceOperation = new BalanceOperationHandler();
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        assertTrue(balanceOperation.doOperation(fruitModel));
        assertTrue(storageDao.containsKey(fruitModel.getName()));
        assertTrue(storageDao.getAmount(fruitModel.getName()) == 10);
    }

     */
}
