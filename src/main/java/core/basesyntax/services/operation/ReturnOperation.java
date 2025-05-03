package core.basesyntax.services.operation;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.model.Product;
import core.basesyntax.services.transaction.model.ProductTransaction;

public class ReturnOperation implements OperationHandler {
    private final ProductDao productDao;

    public ReturnOperation(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void handle(ProductTransaction transaction) {
        int newQuantity = transaction.getQuantity()
                + productDao.get(transaction.getProductName()).getCount();
        productDao.update(new Product(transaction.getProductName(), newQuantity));
    }
}
