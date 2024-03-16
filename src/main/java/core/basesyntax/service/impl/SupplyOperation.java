package core.basesyntax.service.impl;

import core.basesyntax.dao.RecordDao;
import core.basesyntax.dao.RecordDaoImpl;
import core.basesyntax.model.Product;
import core.basesyntax.service.RecordDataManipulation;

public class SupplyOperation implements RecordDataManipulation {
    private final RecordDao recordDao = RecordDaoImpl.getInstance();

    @Override
    public void operate(Product product) {
        if (product.getCount() < 0) {
            throw new IllegalArgumentException("Count cannot be negative. Product: "
                    + product.getName());
        }
        Product productFromDB = recordDao.get(product);
        if (productFromDB == null) {
            recordDao.put(product);
            return;
        }
        productFromDB.setCount(productFromDB.getCount() + product.getCount());
        recordDao.put(productFromDB);
    }
}
