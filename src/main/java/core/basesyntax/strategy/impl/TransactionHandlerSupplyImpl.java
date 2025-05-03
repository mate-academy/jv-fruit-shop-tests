package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.impl.FruitsDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.TransactionHandler;
import core.basesyntax.strategy.TransactionStrategy;

public class TransactionHandlerSupplyImpl implements TransactionHandler {

    @Override
    public TransactionStrategy makeTransaction(FruitTransaction transaction) {
        FruitsDao fruitsDao = new FruitsDaoImpl();
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Supplied quantity couldn't be less '0'.\n"
                    + "Invalid data received from input file: supply "
                    + transaction.getFruitName() + " = " + transaction.getQuantity());
        }
        if (fruitsDao.storageAccess().get(transaction.getFruitName()) != null) {
            int result = fruitsDao.storageAccess().get(transaction.getFruitName())
                    + transaction.getQuantity();
            if (result < 0) {
                throw new RuntimeException("Balance couldn't be less '0' "
                        + "after returned: balance " + transaction.getFruitName() + " = " + result);
            }
            fruitsDao.storageAccess().put(transaction.getFruitName(), result);
            return new TransactionStrategyImpl();
        }
        fruitsDao.storageAccess().put(transaction.getFruitName(), transaction.getQuantity());
        return new TransactionStrategyImpl();
    }
}


