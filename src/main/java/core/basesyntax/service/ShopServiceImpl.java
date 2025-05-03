package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.Map;

public class ShopServiceImpl implements ShopService {
    private OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public Map<String, Integer> process(List<FruitTransaction> transactions) {
        for (FruitTransaction fruitTransaction : transactions) {
            operationStrategy.makeOperation(
                    fruitTransaction.getOperation(),
                    fruitTransaction.getFruit(),
                    fruitTransaction.getQuantity()
            );
            if (fruitTransaction.getOperation() == FruitTransaction.Operation.BALANCE) {
                processBalanceOperation(fruitTransaction);
            }
        }

        return Storage.getCalculatedTransactions();
    }

    private void processBalanceOperation(FruitTransaction fruitTransaction) {
        if (!Storage.getCalculatedTransactions().containsKey(fruitTransaction.getFruit())) {
            Storage.getCalculatedTransactions().put(fruitTransaction.getFruit(),
                    fruitTransaction.getQuantity());
        }
    }
}
