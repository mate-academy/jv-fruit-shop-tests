package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        if (transactions == null) {
            throw new RuntimeException("Transactions are null!!!");
        }
        for (FruitTransaction transaction : transactions) {
            if (transaction.getOperation() == null || transaction.getFruit() == null
                    || transaction.getQuantity() == 0) {
                throw new RuntimeException("Check transactions list! It has dta with null!");
            }
            OperationHandler handler = operationStrategy.getHandler(transaction.getOperation());
            handler.handle(transaction);
        }

    }
}
