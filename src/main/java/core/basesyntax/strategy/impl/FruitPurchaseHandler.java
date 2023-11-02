package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.exception.GoodsLessQuantityException;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.StorageUpdateHandler;
import java.util.NoSuchElementException;

public class FruitPurchaseHandler implements StorageUpdateHandler {
    private static final String NULL_INPUT_MESSAGE = "Invalid operation. "
            + "Input data should not be null!";
    private static final String SEARCHING_FAILURE_MESSAGE = "Can`t find product {%s} in storage";
    private static final String PURCHASING_FAILURE_MESSAGE = "Amount of purchased"
            + " {%s} cannot be more than product amount!";
    private final FruitDao fruitDao;

    public FruitPurchaseHandler() {
        fruitDao = new FruitDaoImpl();
    }

    @Override
    public void updateStorage(String fruitName, int amount) {
        if (fruitName == null) {
            throw new RuntimeException(NULL_INPUT_MESSAGE);
        }
        checkAbilityToUpdate(fruitName, amount);
        fruitDao.remove(fruitName, amount);
    }

    @Override
    public boolean isServiceApplicable(String operationCode) {
        if (operationCode == null) {
            throw new RuntimeException(NULL_INPUT_MESSAGE);
        }
        return Operation.PURCHASE.getCode().equals(operationCode);
    }

    private void checkAbilityToUpdate(String fruit, int amount) {
        Integer actualAmount = fruitDao.get(fruit);
        if (actualAmount == null) {
            throw new NoSuchElementException(String
                    .format(SEARCHING_FAILURE_MESSAGE, fruit));
        }
        if (actualAmount < amount) {
            throw new GoodsLessQuantityException(String
                    .format(PURCHASING_FAILURE_MESSAGE, fruit));
        }
    }
}
