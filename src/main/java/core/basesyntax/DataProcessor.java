package core.basesyntax;

import java.util.List;

public class DataProcessor {
    private final FruitDB fruitDB;
    private final DefaultDataOperationStrategy operationStrategy;

    public DataProcessor(FruitDB fruitDB, DefaultDataOperationStrategy operationStrategy) {
        this.fruitDB = fruitDB;
        this.operationStrategy = operationStrategy;
    }

    public void process(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            operationStrategy.execute(transaction, fruitDB);
        }
    }
}
