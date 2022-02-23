package core.basesyntax.strategy.handlers;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitModel;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.validator.CommodityValidator;

public class BalanceOperationHandler implements OperationHandler, CommodityValidator {
    private static final String OPERATION_NAME = "Balance";

    @Override
    public boolean doOperation(FruitModel fruitModel) {
        isFruitAmountCorrect(fruitModel, OPERATION_NAME);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        if (storageDao.containsKey(fruitModel.getName())) {
            throw new RuntimeException("Operation balance Runtime error "
                    + System.lineSeparator() + "Fruit " + fruitModel.getName()
                    + " already exists.");
        }
        storageDao.putFruitModel(fruitModel);
        return true;
    }
}
