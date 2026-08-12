package core.basesyntax.strategy;

public class TransactionHandler {
    private final OperationStrategy operationStrategy;

    public TransactionHandler(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    public void process(String data) {
        String[] parts = data.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Input data not properly formatted");
        }
        String operation = parts[0];
        String fruitName = parts[1];
        int amount;
        try {
            amount = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Amount must be a valid number, but was: "
                    + parts[2], e);
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount need to be greater than 0, but was: "
                    + amount);
        }

        operationStrategy.getOperationType(operation).process(fruitName, amount);
    }
}
