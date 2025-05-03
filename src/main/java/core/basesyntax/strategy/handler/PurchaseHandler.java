package core.basesyntax.strategy.handler;

import core.basesyntax.db.FruitDao;
import core.basesyntax.db.FruitDaoImpl;
import core.basesyntax.service.TransactionHandler;

public class PurchaseHandler implements TransactionHandler {
    @Override
    public boolean updateStorage(String fruitName, int quantity) {
        FruitDao dao = new FruitDaoImpl();
        if (!dao.contains(fruitName)) {
            throw new RuntimeException(fruitName + " doesn't exist in the storage");
        }
        int currentQuantity = dao.getQuantityByName(fruitName);
        if (currentQuantity < quantity) {
            throw new RuntimeException(quantity + " " + fruitName + "s is not available");
        }
        int newQuantity = currentQuantity - quantity;
        dao.addString(fruitName, newQuantity);
        if (newQuantity == 0) {
            dao.remove(fruitName);
        }
        return true;
    }
}
