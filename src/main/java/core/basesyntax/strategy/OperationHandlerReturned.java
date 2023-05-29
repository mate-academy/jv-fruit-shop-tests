package core.basesyntax.strategy;

import core.basesyntax.FruitDaoImpl;

public class OperationHandlerReturned implements OperationHandler {
    private final FruitDaoImpl fruitDao = new FruitDaoImpl();

    @Override
    public void handle(String key, String value) {
        fruitDao.get(key)
                .setReturned(fruitDao.get(key)
                        .getReturned() + Integer.parseInt(value));
    }
}
