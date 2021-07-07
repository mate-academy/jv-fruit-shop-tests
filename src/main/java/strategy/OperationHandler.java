package strategy;

@FunctionalInterface
public interface OperationHandler {
    void changeBalance(String fruit, Integer quantity);
}
