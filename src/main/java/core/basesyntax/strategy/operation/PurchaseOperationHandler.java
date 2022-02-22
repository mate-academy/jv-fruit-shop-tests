package core.basesyntax.strategy.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationHandler implements OperationHandler {
    private FruitDao fruitDao = new FruitDaoImpl();

    @Override
    public void changeData(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new RuntimeException("Fruit transaction can`t be null");
        }
        if (fruitTransaction.getFruitType() == null) {
            throw new RuntimeException("Fruit type can`t be null");
        }
        if (fruitTransaction.getAmount() < 0) {
            throw new RuntimeException("Fruit amount can`t be negative");
        }
        Fruit newFruit = new Fruit();
        Fruit fruitFromStorage = fruitDao.get(fruitTransaction.getFruitType());
        if (fruitFromStorage.getAmount() < fruitTransaction.getAmount()) {
            throw new RuntimeException("Fruit amount in storage can`t be negative");
        }
        newFruit.setFruitType(fruitTransaction.getFruitType());
        newFruit.setAmount(fruitFromStorage.getAmount() - fruitTransaction.getAmount());
        fruitDao.update(newFruit);
    }
}
