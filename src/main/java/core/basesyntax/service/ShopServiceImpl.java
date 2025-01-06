package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public List<FruitTransaction> process(List<FruitTransaction> transactions) {
        for (FruitTransaction fruitTransaction : transactions) {
            FruitTransaction calculatedTransaction =
                    Storage.getCalculatedTransactions().get(fruitTransaction.getFruit());
            if (calculatedTransaction != null) {
                operationStrategy.makeOperation(
                        fruitTransaction.getOperation(),
                        calculatedTransaction,
                        fruitTransaction.getQuantity()
                );
            }
            if (fruitTransaction.getOperation() == FruitTransaction.Operation.BALANCE
                    && !Storage.getCalculatedTransactions()
                    .containsKey(fruitTransaction.getFruit())) {
                Storage.getCalculatedTransactions()
                        .put(fruitTransaction.getFruit(), fruitTransaction);
            }
        }

        return Storage.getFruitTransactionList();
    }
}
