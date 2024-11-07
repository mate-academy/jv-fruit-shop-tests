package core.basesyntax.service.operations;

import core.basesyntax.db.StockDao;

public class Balance implements Operation {
    private final StockDao stockDao;

    public Balance(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public void update(String product, Integer amount) {
        stockDao.set(product, amount);
    }
}
