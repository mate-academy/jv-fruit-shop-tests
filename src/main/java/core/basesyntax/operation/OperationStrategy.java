package core.basesyntax.operation;

public interface OperationStrategy {
    OperationHandler getOperationHandler(String operation);
}
