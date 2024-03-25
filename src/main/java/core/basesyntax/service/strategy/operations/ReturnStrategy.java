package core.basesyntax.service.strategy.operations;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.exceptions.NotEnoughFruitsException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class ReturnStrategy implements OperationHandler {
    private static final ProductDao productDao = new ProductDaoImpl();

    @Override
    public int handle(FruitTransaction transaction) {
        validateTransaction(transaction);
        String fruitName = transaction.getFruit();
        Integer fruitsInDB = productDao.getValue(fruitName);
        int newFruitNumber = fruitsInDB == null ? transaction.getQuantity() :
                fruitsInDB + transaction.getQuantity();
        return productDao.add(fruitName, newFruitNumber);
    }

    private void validateTransaction(FruitTransaction transaction) {
        if (transaction == null) {
            throw new NullDataException("Transaction can't be NULL");
        }

        if (transaction.getQuantity() <= 0) {
            throw new NotEnoughFruitsException("You can't return less then 0 fruits");
        }
    }
}
