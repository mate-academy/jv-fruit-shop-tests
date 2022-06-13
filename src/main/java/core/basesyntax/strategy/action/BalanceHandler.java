package core.basesyntax.strategy.action;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.model.ProductTransaction;

public class BalanceHandler implements ActionHandler {
    @Override
    public void process(ProductDao productDao, ProductTransaction transaction) {
        productDao.setQuantity(transaction.getProduct(), transaction.getQuantity());
    }
}
