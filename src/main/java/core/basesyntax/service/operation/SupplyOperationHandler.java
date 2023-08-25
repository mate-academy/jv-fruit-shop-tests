package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;

public class SupplyOperationHandler implements OperationHandler {
    private final FruitDao fruitDao;

    public SupplyOperationHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void processTransaction(String fruit, Integer quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Supply can`t be negative: " + quantity);
        }

        if (fruitDao.get(fruit) != null) {
            fruitDao.add(fruit, fruitDao.get(fruit) + quantity);
        } else {
            fruitDao.add(fruit, quantity);
        }

        System.out.println("Fruit: " + fruit + " SupplyOperationHandler: " + fruitDao.get(fruit));
    }
}
