package core.basesyntax.service.strategy.operations;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.exceptions.NotEnoughFruitsException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class BalanceStrategy implements OperationHandler {
    private static final ProductDao productDao = new ProductDaoImpl();

    @Override
    public int handle(FruitTransaction transaction) {
        validateTransaction(transaction);
        return productDao.add(transaction.getFruit(), transaction.getQuantity());
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
