package core.basesyntax.serviceimpl.transactionimpl;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.serviceintrface.operation.OperationHandler;
import core.basesyntax.strategy.serviceintrface.operation.OperationStrategy;
import core.basesyntax.strategy.serviceintrface.operation.model.FruitTransaction;
import core.basesyntax.strategy.serviceintrface.transaction.FruitTransactionHandler;
import java.util.List;

public class FruitTransactionsHandlerImpl implements FruitTransactionHandler {
    private final Storage fruitStorage;
    private OperationStrategy operationStrategy;

    public FruitTransactionsHandlerImpl(Storage fruitStorage,
                                        OperationStrategy operationStrategy) {
        this.fruitStorage = fruitStorage;
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processTransactionsList(List<FruitTransaction> fruitTransactionList) {
        fruitTransactionList.forEach(transaction -> {
            OperationHandler operationHandler = operationStrategy
                    .getHandler(transaction.getOperation());
            fruitStorage.updateFruitQuantity(transaction.getFruit(),
                    operationHandler.getFruitAmount(transaction.getQuantity()));
        });
    }
}

