package core.basesyntax.strategy.handler;

import core.basesyntax.db.FruitDao;
import core.basesyntax.db.FruitDaoImpl;
import core.basesyntax.service.TransactionHandler;

public class ReturnHandler implements TransactionHandler {
    @Override
    public boolean updateStorage(String fruitName, int quantity) {
        FruitDao dao = new FruitDaoImpl();
        if (!dao.contains(fruitName)) {
            dao.addString(fruitName, quantity);
            return true;
        }
        int currentQuantity = dao.getQuantityByName(fruitName);
        dao.addString(fruitName, currentQuantity + quantity);
        return true;
    }
}
