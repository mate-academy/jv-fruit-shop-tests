package core.basesyntax.service.impl;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
//
public class FruitShopServiceImpl implements FruitShopService {
    private final OperationStrategy operationStrategy;

    public FruitShopServiceImpl(OperationStrategy operationStrategy) {
        if (operationStrategy == null) {
            throw new ValidationException("Can't make operations with null data");
        } else {
            this.operationStrategy = operationStrategy;
        }
    }

    @Override
    public void getFruitBalance(List<FruitTransaction> fruitTransactions) {
        if (fruitTransactions == null) {
            throw new ValidationException("Can't make operations with null data");
        } else if (fruitTransactions.isEmpty()) {
            throw new ValidationException("Can't make operations with empty data");
        }

        for (var fruitTransaction : fruitTransactions) {
            if (fruitTransaction.getName().isEmpty()) {
                throw new ValidationException("Can't make operations with empty Fruit Name");
            } else if (fruitTransaction.getQuantity() < 0) {
                throw new ValidationException("Can't make operations with negative Quantity");
            } else if (fruitTransaction.getOperationType() == null
                    || fruitTransaction.getOperationType().getCode().isEmpty()
                    || fruitTransaction.getOperationType().getCode()
                    == OperationType.NONE.getCode()) {
                throw new ValidationException("Can't make operations without Operation Type");
            } else {
                operationStrategy.getOperation(fruitTransaction.getOperationType())
                            .handle(fruitTransaction);
            }
        }
    }
}
