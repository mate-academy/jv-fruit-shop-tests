package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataHandlerService;
import core.basesyntax.strategy.OperationProcessingStrategy;
import java.util.List;
import java.util.function.Consumer;

public class DataHandlerServiceImpl implements DataHandlerService {
    private OperationProcessingStrategy processingStrategy;

    public DataHandlerServiceImpl(OperationProcessingStrategy processingStrategy) {
        this.processingStrategy = processingStrategy;
    }

    @Override
    public boolean handleData(List<FruitTransaction> fruitTransactions) {
        if (fruitTransactions == null) {
            throw new RuntimeException("Fruit transactions list can't be null");
        }
        fruitTransactions.forEach(getFruitTransactionConsumer());
        return true;
    }

    private Consumer<FruitTransaction> getFruitTransactionConsumer() {
        Consumer<FruitTransaction> consumer = transaction
                -> processingStrategy.get(transaction.getOperation())
                .doAction(transaction.getFruit(), transaction.getAmount());
        return consumer;
    }
}
