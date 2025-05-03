package core.basesyntax.strategy.handler;

import core.basesyntax.db.FruitDao;
import core.basesyntax.db.FruitDaoImpl;
import core.basesyntax.service.TransactionHandler;

public class BalanceHandler implements TransactionHandler {
    @Override
    public boolean updateStorage(String fruitName, int quantity) {
        FruitDao dao = new FruitDaoImpl();
        if (dao.contains(fruitName)) {
            throw new RuntimeException(fruitName + " already exists in the storage");
        }
        dao.addString(fruitName, quantity);
        return true;
    }
}
