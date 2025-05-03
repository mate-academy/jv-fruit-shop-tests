package core.basesyntax.strategy.handlers;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitModel;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.validator.CommodityValidator;
import core.basesyntax.strategy.validator.StorageValidator;

public class PurchaseOperationHandler implements OperationHandler, StorageValidator,
        CommodityValidator {
    private static final String OPERATION_NAME = "Purchase";

    @Override
    public boolean doOperation(FruitModel fruitModel) {
        doesStorageContainsFruit(fruitModel, OPERATION_NAME);
        isFruitAmountCorrect(fruitModel, OPERATION_NAME);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        int amountAfterPurchase = storageDao.getAmount(fruitModel.getName())
                - fruitModel.getAmount();
        if (amountAfterPurchase < 0) {
            throw new RuntimeException("Operation Purchase. " + System.lineSeparator()
                    + "There is not enough fruit " + fruitModel.getName()
                    + System.lineSeparator() + " Required " + fruitModel.getAmount()
                    + " but there is " + storageDao.getAmount(fruitModel.getName()));
        }
        storageDao.replaceWithNewAmount(fruitModel.getName(), amountAfterPurchase);
        return true;
    }
}
