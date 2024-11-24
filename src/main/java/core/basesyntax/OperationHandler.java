package core.basesyntax;

@FunctionalInterface
interface OperationHandler {
    void apply(String fruit, int quantity);
}
