package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDaoImpl;

public class OperationHandlerSupply implements OperationHandler {
    private final FruitDaoImpl fruitDao = new FruitDaoImpl();

    @Override
    public void handle(String key, String value) {
        fruitDao.get(key)
                .setSupply(fruitDao.get(key)
                        .getSupply() + Integer.parseInt(value));
    }
}
