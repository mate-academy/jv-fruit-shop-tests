package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;
import java.util.Map;

public class FruitServiceImpl implements FruitService {
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    public FruitServiceImpl(Map<FruitTransaction.Operation, OperationHandler> operationHandlers) {
        this.operationHandlers = operationHandlers;
    }

    @Override
    public void applyTransaction(FruitTransaction transaction) {
        OperationHandler handler = operationHandlers.get(transaction.getOperation());
        handler.apply(transaction);
    }

    @Override
    public void applyTransactions(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            applyTransaction(transaction);
        }
    }

    @Override
    public Map<String, Integer> getReportData() {
        return Storage.getFruitStorage();
    }
}
