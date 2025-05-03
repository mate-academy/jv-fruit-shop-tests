package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.StorageUpdateHandler;

public class FruitSupplyHandler implements StorageUpdateHandler {
    private static final String NULL_INPUT_MESSAGE = "Invalid operation. "
            + "Input data should not be null!";
    private final FruitDao fruitDao;

    public FruitSupplyHandler() {
        fruitDao = new FruitDaoImpl();
    }

    @Override
    public void updateStorage(String fruitName, int amount) {
        if (fruitName == null) {
            throw new RuntimeException(NULL_INPUT_MESSAGE);
        }
        fruitDao.add(fruitName, amount);
    }

    @Override
    public boolean isServiceApplicable(String operationCode) {
        if (operationCode == null) {
            throw new RuntimeException(NULL_INPUT_MESSAGE);
        }
        return Operation.SUPPLY.getCode().equals(operationCode);
    }
}
