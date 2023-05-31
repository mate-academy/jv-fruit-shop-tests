package core.basesyntax.strategy;

public interface OperationStrategy {
    OperationHandler getOperationStrategy(String operation);
}
