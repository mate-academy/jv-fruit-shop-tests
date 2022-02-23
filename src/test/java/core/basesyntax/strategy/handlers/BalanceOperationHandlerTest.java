package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitModel;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.strategy.validator.CommodityValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static CommodityValidator commodityValidator;

    @Before
    public void setUp() throws Exception {
        commodityValidator = new BalanceOperationHandler();
    }

    @After
    public void tearDown() throws Exception {
        FruitStorage.fruitStorage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void isFruitAmountCorrect_negativeAmount_NotOk() {
        FruitModel fruitModel = new FruitModel("apple", -10);
        commodityValidator.isFruitAmountCorrect(fruitModel, "b");
    }

    @Test
    public void isFruitAmountCorrect_correctData_Ok() {
        FruitModel fruitModel = new FruitModel("Kevin_the_minion", 10);
        assertTrue(commodityValidator.isFruitAmountCorrect(fruitModel, "b"));
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_fruitIsAlreadyExists_NotOk() {
        BalanceOperationHandler balanceOperation = new BalanceOperationHandler();
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        storageDao.putFruitModel(fruitModel);
        balanceOperation.doOperation(fruitModel);
    }
}
