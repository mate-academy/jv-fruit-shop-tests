package core.basesyntax.strategy;

public class TransactionHandler {
    private final OperationStrategy operationStrategy;

    public TransactionHandler(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    public void process(String data) {
        String[] parts = data.split(",");
        if (parts.length != 3) {
            throw new ArrayIndexOutOfBoundsException("Data must contain 3 parts");
        }
        String operation = parts[0];
        String fruitName = parts[1];
        if (Integer.parseInt(parts[2]) <= 0) {
            throw new IllegalArgumentException(
                    "Amount need to be greater than 0, but was: "
                            + Integer.parseInt(parts[2]));
        }
        int amount = Integer.parseInt(parts[2]);

        operationStrategy.getOperationType(operation).process(fruitName, amount);
    }
}
