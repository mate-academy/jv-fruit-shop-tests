package core.basesyntax.servise.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.servise.FruitTransactionProcessorService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class FruitTransactionProcessorServiceImpl implements FruitTransactionProcessorService {
    private final OperationStrategy operationStrategy;
    private final StorageDao storageDao;

    public FruitTransactionProcessorServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
        storageDao = new StorageDaoImpl();
    }

    @Override
    public void process(List<FruitTransaction> fruitTransactions) {
        for (FruitTransaction transaction : fruitTransactions) {
            if (transaction.getFruit().equals("") || transaction.getFruit() == null) {
                throw new RuntimeException("Fruit can`t be null or empty" + transaction.getFruit());
            }
            if (transaction.getQuantity() < 0) {
                throw new RuntimeException("Quantity can`t be negative"
                        + transaction.getQuantity());
            }
        }
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            operationStrategy.get(fruitTransaction.getOperation())
                    .handle(fruitTransaction.getFruit(),
                            fruitTransaction.getQuantity());
        }
    }
}
