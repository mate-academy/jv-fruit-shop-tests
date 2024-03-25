package core.basesyntax.service.strategy.operations;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.exceptions.NotEnoughFruitsException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class PurchaseStrategy implements OperationHandler {
    private static final ProductDao productDao = new ProductDaoImpl();

    @Override
    public int handle(FruitTransaction transaction) {
        validateTransaction(transaction);
        String fruitName = transaction.getFruit();
        Integer temporaryValue = productDao.getValue(fruitName);

        int newFruitNumber = temporaryValue == null ? -transaction.getQuantity() :
                temporaryValue - transaction.getQuantity();

        if (newFruitNumber < 0) {
            throw new NotEnoughFruitsException("Not enough fruits to buy "
                    + transaction.getQuantity()
                    + " " + fruitName);
        }
        return productDao.add(fruitName, newFruitNumber);
    }

    private void validateTransaction(FruitTransaction transaction) {
        if (transaction == null) {
            throw new NullDataException("Transaction can't be NULL");
        }

        if (transaction.getQuantity() <= 0) {
            throw new NotEnoughFruitsException("You can't purchase less then 0 fruits");
        }
    }
}
