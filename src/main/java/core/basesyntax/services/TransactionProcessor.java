package core.basesyntax.services;

import static core.basesyntax.transactions.FruitsTransaction.Operation;
import static core.basesyntax.transactions.FruitsTransaction.Operation.BALANCE;
import static core.basesyntax.transactions.FruitsTransaction.Operation.PURCHASE;
import static core.basesyntax.transactions.FruitsTransaction.Operation.RETURN;
import static core.basesyntax.transactions.FruitsTransaction.Operation.SUPPLY;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.transactions.FruitsTransaction;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class TransactionProcessor implements TransactionService {
    private final FruitsDao fruitsDao;
    private Map<Operation, BiConsumer<String, Integer>> operationsMap;

    public TransactionProcessor(FruitsDao fruitsDao) {
        this.fruitsDao = fruitsDao;
        initializeOperations();
    }

    private void initializeOperations() {
        operationsMap = Map.of(
                BALANCE, fruitsDao::balance,
                SUPPLY, fruitsDao::supply,
                PURCHASE, fruitsDao::purchase,
                RETURN, fruitsDao::returnFruits
        );
    }

    @Override
    public void processTransactions(List<FruitsTransaction> transactions) {
        for (FruitsTransaction tr : transactions) {
            if (tr.getOperation() == null) {
                throw new IllegalArgumentException("Unknown operation: null");
            }
            BiConsumer<String, Integer> operation = this.operationsMap.get(tr.getOperation());
            if (operation == null) {
                throw new IllegalArgumentException("Unknown operation: "
                        + tr.getOperation());
            }
            operation.accept(tr.getFruit(), tr.getQuantity());
        }
    }
}
