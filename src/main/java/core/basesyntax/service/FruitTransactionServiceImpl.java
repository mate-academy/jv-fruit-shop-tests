package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FruitTransactionServiceImpl implements FruitTransactionService {
    private static final FruitTransaction.Operation
            OPERATION_TOTAL = FruitTransaction.Operation.TOTAL;
    private OperationStrategy operationStrategy;

    public FruitTransactionServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public Map<String, List<FruitTransaction>> readFruitTransaction() {
        return Storage.fruitTransactions.stream()
                .collect(Collectors.groupingBy(FruitTransaction::getFruit,
                        Collectors.mapping(fruitTransaction -> fruitTransaction,
                                Collectors.toList())));
    }

    @Override
    public Map<String, Integer> calcFruitTransaction(Map<String,
            List<FruitTransaction>> transactionMap) {
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, List<FruitTransaction>> entry : transactionMap.entrySet()) {
            int summaResult = 0;
            List<FruitTransaction> fruitTransactions = entry.getValue();
            for (FruitTransaction fruitTransaction : fruitTransactions) {
                summaResult = operationStrategy.get(fruitTransaction.getOperation())
                        .getOperation(summaResult,fruitTransaction.getQuantity());
            }
            if (summaResult < 0) {
                throw new RuntimeException("Balance must not be negative");
            }
            FruitTransaction newFruitTransaction = new FruitTransaction();
            newFruitTransaction.setOperation(OPERATION_TOTAL);
            newFruitTransaction.setFruit(entry.getKey());
            newFruitTransaction.setQuantity(summaResult);
            result.put(entry.getKey(), Integer.valueOf(summaResult));
            Storage.fruitTransactions.add(newFruitTransaction);
        }
        return result;
    }
}
