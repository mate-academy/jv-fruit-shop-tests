package core.basesyntax.service.operations;

import core.basesyntax.db.StockDao;

public class Supply implements Operation {
    private final StockDao stockDao;

    public Supply(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public void update(String product, Integer amount) {
        stockDao.increase(product, amount);
    }
}
