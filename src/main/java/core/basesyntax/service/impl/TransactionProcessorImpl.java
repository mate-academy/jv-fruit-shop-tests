package core.basesyntax.service.impl;

import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import java.util.Map;

public class TransactionProcessorImpl implements TransactionProcessor {
    private static final Integer UPPER_BOUND = 1000;
    private final OperationStrategy operationStrategy;

    public TransactionProcessorImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public Map<String, Integer> process(List<FruitTransaction> fruitTransactions) {
        return fruitTransactions.stream()
                .map(fruitTransaction -> operationStrategy.get(fruitTransaction.getOperation())
                        .getOperationResult(fruitTransaction))
                .collect(groupingBy(FruitTransaction::getFruit,
                        filtering(fruitTransaction -> fruitTransaction.getQuantity() < UPPER_BOUND,
                                summingInt(FruitTransaction::getQuantity))));
    }
}
