package core.basesyntax.service.strategy.operations;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.exceptions.NotEnoughFruitsException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class SupplyStrategy implements OperationHandler {
    private static final ProductDao productDao = new ProductDaoImpl();

    @Override
    public int handle(FruitTransaction transaction) {
        validateTransaction(transaction);
        String fruitName = transaction.getFruit();
        Integer temporary = productDao.getValue(fruitName);
        int newFruitNumber = temporary == null ? transaction.getQuantity() :
                productDao.getValue(fruitName) + transaction.getQuantity();
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
