package core.basesyntax.dataprocessor;

import core.basesyntax.service.FruitTransaction;
import java.util.List;

public class DataProcessor {
    private final DefaultDataOperationStrategy operationStrategy;

    public DataProcessor(DefaultDataOperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    public void process(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            if (transaction.getQuantity() < 0) {
                throw new IllegalArgumentException("Transaction quantity cannot be negative");
            }
            Operation operation = Operation.fromCode(transaction.getType());
            OperationHandler handler = operationStrategy.getHandler(operation);
            handler.apply(transaction.getFruit(), transaction.getQuantity());
        }
    }
}
