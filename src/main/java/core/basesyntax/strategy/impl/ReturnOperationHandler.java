package core.basesyntax.strategy.impl;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.impl.ProductDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    private final ProductDao productDao;

    public ReturnOperationHandler() {
        this.productDao = new ProductDaoImpl();
    }

    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction == null
                || transaction.getFruit() == null
                || transaction.getQuantity() < 0) {
            throw new RuntimeException("Can't handle null or wrong transaction data");
        }
        productDao.addAmount(transaction.getFruit(), transaction.getQuantity());
    }
}
