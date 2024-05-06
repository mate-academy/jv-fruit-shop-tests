package core.basesyntax.strategy;

import core.basesyntax.service.QuantityCounter;
import core.basesyntax.transaction.Transaction;
import java.util.Map;

public class QuantityCounterStrategyImpl implements QuantityCounterStrategy {
    private final Map<Transaction.Operation, QuantityCounter> quantityCounters;

    public QuantityCounterStrategyImpl(Map<Transaction.Operation,
            QuantityCounter> quantityCounters) {
        this.quantityCounters = quantityCounters;
    }

    @Override
    public QuantityCounter get(Transaction operation) {
        isNullMap(quantityCounters);
        isEmptyMap(quantityCounters);
        isNullTransaction(operation);
        isNullOperationInTransaction(operation);
        return quantityCounters.get(operation.getOperation());
    }

    private void isEmptyMap(Map<Transaction.Operation, QuantityCounter> quantityCounters) {
        if (quantityCounters.isEmpty()) {
            throw new IllegalArgumentException("Map can't be empty");
        }
    }

    private void isNullOperationInTransaction(Transaction operation) {
        if (operation.getOperation() == null) {
            throw new IllegalArgumentException("Operation in Transaction can't be null");
        }
    }

    private void isNullTransaction(Transaction operation) {
        if (operation == null) {
            throw new IllegalArgumentException("Transaction can't be null");
        }
    }

    private void isNullMap(Map<Transaction.Operation, QuantityCounter> quantityCounters) {
        if (quantityCounters == null) {
            throw new NullPointerException("Map can't be null");
        }
    }
}
