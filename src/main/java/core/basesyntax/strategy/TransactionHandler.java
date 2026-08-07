package core.basesyntax.strategy;

public class TransactionHandler {
    private final OperationStrategy operationStrategy;

    public TransactionHandler(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    public void process(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        String[] parts = data.split(",");
        if (parts.length != 3) {
            throw new ArrayIndexOutOfBoundsException("Data must contain 3 parts");
        }
        String operation = parts[0];
        String fruitName = parts[1];
        int amount = Integer.parseInt(parts[2]);

        operationStrategy.get(operation).process(fruitName, amount);
    }
}
