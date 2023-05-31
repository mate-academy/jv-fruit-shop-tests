package core.basesyntax.strategy;

public interface Strategy {
    void addStrategyType(String operationType, OperationHandler operationHandler);

    OperationHandler getStrategyType(String type);
}
