package core.basesyntax.service.impl;

import core.basesyntax.db.FruitShopDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.strategy.FruitStrategy;
import java.util.List;

public class FruitTransactionServiceImpl implements FruitTransactionService {
    private FruitShopDao fruitShopDao;
    private FruitStrategy fruitStrategy;

    public FruitTransactionServiceImpl(FruitShopDao fruitShopDao, FruitStrategy fruitStrategy) {
        this.fruitShopDao = fruitShopDao;
        this.fruitStrategy = fruitStrategy;
    }

    @Override
    public String processTransactions(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            fruitStrategy.getOperationHandler(transaction.getOperation())
                    .handleOperation(transaction);
        }
        return fruitShopDao.getAllFruitsAndQuantities().entrySet().stream().toList().toString();
    }
}
