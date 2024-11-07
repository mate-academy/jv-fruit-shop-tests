package core.basesyntax.service.operations;

import core.basesyntax.db.StockDao;

public class Purchase implements Operation {
    private final StockDao stockDao;

    public Purchase(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public void update(String product, Integer amount) {
        stockDao.decrease(product, amount);
    }
}
