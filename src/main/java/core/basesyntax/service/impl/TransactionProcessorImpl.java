package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StrategyService;
import core.basesyntax.service.TransactionProcessor;
import java.util.List;

public class TransactionProcessorImpl implements TransactionProcessor {
    private StrategyService strategyService;

    public TransactionProcessorImpl(StrategyService strategyService) {
        this.strategyService = strategyService;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        if (transactions.isEmpty()) {
            throw new RuntimeException("Transaction list is empty");
        }
        for (FruitTransaction transaction : transactions) {
            strategyService.get(transaction.getOperation()).operationProcess(transaction);
        }
    }
}
