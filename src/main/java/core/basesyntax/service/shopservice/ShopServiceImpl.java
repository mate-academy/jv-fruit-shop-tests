package core.basesyntax.service.shopservice;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;
    private final FruitStorageDao fruitStorageDao;

    public ShopServiceImpl(OperationStrategy operationStrategy, FruitStorageDao fruitStorageDao) {
        this.operationStrategy = operationStrategy;
        this.fruitStorageDao = fruitStorageDao;
    }

    @Override
    public int process(List<FruitTransaction> fruitTransactions) {
        for (FruitTransaction transaction : fruitTransactions) {
            OperationHandler operationHandler = operationStrategy
                    .getOperation(transaction.getOperation());
            operationHandler.handle(transaction);
        }
        return fruitStorageDao.calculateTotalQuantity();
    }
}
