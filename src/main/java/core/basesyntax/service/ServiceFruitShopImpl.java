package core.basesyntax.service;

import core.basesyntax.db.FruitTransactionDb;
import core.basesyntax.db.FruitTransactionDbImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.Strategy;
import java.util.List;
import java.util.Map;

public class ServiceFruitShopImpl implements ServiceFruitShop {
    private Map<FruitTransaction.Operation, Strategy> operationStrategyMap;

    public ServiceFruitShopImpl(Map<FruitTransaction.Operation, Strategy> operationStrategyMap) {
        this.operationStrategyMap = operationStrategyMap;
    }

    @Override
    public FruitTransactionDb processTransaction(List<FruitTransaction> fruitTransaction) {
        FruitTransactionDb fruitTransactionDB = new FruitTransactionDbImpl();

        for (FruitTransaction transaction : fruitTransaction) {
            Strategy strategy = operationStrategyMap.get(transaction.getOperation());
            if (strategy != null) {
                strategy.calculation(transaction, fruitTransactionDB);
            }
        }
        return fruitTransactionDB;
    }
}
