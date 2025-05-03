package core.basesyntax.service;

import core.basesyntax.exceptions.InvalidOperationException;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.transaction.FruitTransaction;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processTransactions(List<FruitTransaction> transactionList) {
        checkTransactionList(transactionList);
        transactionList.forEach(f ->
                operationStrategy.getOperationHandler(f.getOperation()).apply(f));
    }

    private void checkTransactionList(List<FruitTransaction> transactionsList) {
        for (FruitTransaction a : transactionsList) {
            if (a == null) {
                throw new InvalidOperationException(
                        "Element of the fruit transaction: " + a + " list contains null value!");
            }
        }
    }
}
