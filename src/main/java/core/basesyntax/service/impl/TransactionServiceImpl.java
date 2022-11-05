package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TransactionServiceImpl implements TransactionService {
    private static final int DEFAULT_EMPTY_VALUE = 0;
    private final OperationStrategy operationStrategy;
    private final FruitStorageDao fruitStorageDao;

    public TransactionServiceImpl(OperationStrategy operationStrategy,
                                  FruitStorageDao fruitStorageDao) {
        this.operationStrategy = operationStrategy;
        this.fruitStorageDao = fruitStorageDao;
    }

    @Override
    public void transaction(List<FruitTransaction> fruitTransactions) {
        Optional.ofNullable(fruitTransactions).orElseThrow(
                () -> new NoSuchElementException("Transaction list can`t be null"));
        for (FruitTransaction transaction : fruitTransactions) {
            OperationHandler operation = operationStrategy.getOperation(transaction.getOperation());
            fruitStorageDao
                    .add(transaction.getFruit(), operation
                            .operate(Optional.ofNullable(fruitStorageDao
                                            .get(transaction.getFruit()))
                                            .orElse(DEFAULT_EMPTY_VALUE),
                                    transaction.getQuantity()));
        }
    }
}
