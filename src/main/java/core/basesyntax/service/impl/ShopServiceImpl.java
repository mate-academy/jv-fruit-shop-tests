package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import java.util.Map;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;
    private final FruitDao fruitDao;

    public ShopServiceImpl(OperationStrategy operationStrategy, FruitDao fruitDao) {
        this.operationStrategy = operationStrategy;
        this.fruitDao = fruitDao;
    }

    public Map<Fruit, Integer> updateStorageInfo(List<TransactionDto> transactionDtos) {
        for (TransactionDto transaction : transactionDtos) {
            operationStrategy.get(transaction.getOperation())
                            .apply(transaction);
        }
        return fruitDao.getAll();
    }
}
