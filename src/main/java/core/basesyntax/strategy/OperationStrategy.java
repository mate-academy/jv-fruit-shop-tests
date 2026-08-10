package core.basesyntax.strategy;

public interface OperationStrategy {
    Transaction getOperationType(String operationType);
}
