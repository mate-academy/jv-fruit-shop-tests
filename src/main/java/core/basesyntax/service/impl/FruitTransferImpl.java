package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransfer;
import core.basesyntax.service.ReadScvService;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.List;

public class FruitTransferImpl implements FruitTransfer {
    private OperationStrategy operationStrategy;
    private ReadScvService readScvService;
    private String filePath;

    public FruitTransferImpl(OperationStrategy operationStrategy,
                             ReadScvService readScvService, String filePath) {
        this.operationStrategy = operationStrategy;
        this.readScvService = readScvService;
        this.filePath = filePath;
    }

    @Override
    public void transfer() {
        List<FruitTransaction> fruitTransactions = readScvService.readFromFile(filePath);
        for (FruitTransaction fruit : fruitTransactions) {
            operationStrategy.get(fruit.getOperation()).operation(fruit);
        }
    }
}
