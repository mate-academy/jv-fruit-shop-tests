package core.basesyntax.utils;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.handlers.TransactionHandler;
import core.basesyntax.handlers.impl.BalanceTransactionHandler;
import core.basesyntax.handlers.impl.PurchaseTransactionHandler;
import core.basesyntax.handlers.impl.ReturnTransactionHandler;
import core.basesyntax.handlers.impl.SupplyTransactionHandler;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;

public class StrategyUtil {
    public Map<FruitTransaction.Operation,
            TransactionHandler> initStrategyMap(StorageDao storageDao) {
        Map<FruitTransaction.Operation, TransactionHandler> strategyMap = new HashMap<>();

        TransactionHandler supplyHandler = new SupplyTransactionHandler(storageDao);
        TransactionHandler purchaseHandler = new PurchaseTransactionHandler(storageDao);
        TransactionHandler balanceHandler = new BalanceTransactionHandler(storageDao);
        TransactionHandler returnHandler = new ReturnTransactionHandler(storageDao);

        strategyMap.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        strategyMap.put(FruitTransaction.Operation.RETURN, returnHandler);
        strategyMap.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        strategyMap.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        return strategyMap;
    }
}
