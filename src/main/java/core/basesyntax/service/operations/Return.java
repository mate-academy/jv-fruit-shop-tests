package core.basesyntax.service.operations;

import core.basesyntax.db.StockDao;

public class Return implements Operation {
    private final StockDao stockDao;

    public Return(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public void update(String product, Integer amount) {
        stockDao.increase(product, amount);
    }
}
