package core.basesyntax.strategy.action;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.exception.ActionProductNotFoundException;
import core.basesyntax.model.ProductTransaction;

public class SupplyHandler implements ActionHandler {
    @Override
    public void process(ProductDao productDao, ProductTransaction transaction) {
        String productName = transaction.getProduct();
        int quantity = productDao.getQuantity(productName).orElseThrow(() ->
                new ActionProductNotFoundException(String.format("Product %s not found in storage",
                        productName)));
        quantity += transaction.getQuantity();
        productDao.setQuantity(productName, quantity);
    }
}
