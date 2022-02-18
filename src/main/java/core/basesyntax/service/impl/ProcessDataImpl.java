package core.basesyntax.service.impl;

import core.basesyntax.db.ReportStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.data.ProcessDataService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessDataImpl implements ProcessDataService {
    private final Map<String, Integer> balancesOfFruits = new HashMap<>();
    private final ReportStorage reportStorage = new ReportStorage();
    private final OperationStrategy operationStrategy;

    public ProcessDataImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> listOfTransactions) {
        processOperations(listOfTransactions, operationStrategy.get());
        writeDataToStorage();
    }

    private void processOperations(List<FruitTransaction> listOfTransactions,
                                   Map<Operation, OperationHandler> operationsMap) {
        for (FruitTransaction fruitTransaction : listOfTransactions) {
            balancesOfFruits.put(fruitTransaction.getFruit(),
                    operationsMap.get(fruitTransaction.getOperation())
                            .process(fruitTransaction.getQuantity(),
                                    checkBalance(fruitTransaction.getFruit())));
        }
    }

    private void writeDataToStorage() {
        for (Map.Entry<String, Integer> entry : balancesOfFruits.entrySet()) {
            reportStorage.add(entry.getKey() + "," + entry.getValue());
        }
    }

    private int checkBalance(String key) {
        return balancesOfFruits.getOrDefault(key, 0);
    }
}
