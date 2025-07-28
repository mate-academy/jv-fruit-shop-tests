package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> fruitTransactions) {
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            if (fruitTransaction.getFruit() == null) {
                throw new RuntimeException("Null fruit exception!");
            }
            if (fruitTransaction.getFruit().isEmpty()) {
                throw new RuntimeException("Fruit paramateres are empty!");
            }
            if (fruitTransaction.getAmount() <= 0) {
                throw new RuntimeException("Amount can't be less than 1!");
            }
            if (!Character.isLetter(fruitTransaction.getFruit().charAt(0))) {
                throw new RuntimeException("Fruit name should start from letters!");
            }
            OperationHandler operationHandler = operationStrategy.get(
                    fruitTransaction.getOperation());
            operationHandler.updateNumberOffFruit(fruitTransaction.getFruit(),
                    fruitTransaction.getAmount());
        }
    }
}
