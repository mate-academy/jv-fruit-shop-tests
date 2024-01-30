package core.basesyntax.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    private final FruitDao dao = new FruitDaoImpl();

    @Override
    public void processOperation(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        int amountInStorage = dao.get(fruit);
        int amountToBuy = fruitTransaction.getAmount();
        if (amountInStorage < amountToBuy) {
            throw new RuntimeException("Not enough fruit in the storage for this operation");
        }
        dao.addFruit(fruit, amountInStorage - amountToBuy);
    }
}
