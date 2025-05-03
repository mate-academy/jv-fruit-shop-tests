package core.basesyntax.service.impl;

import core.basesyntax.model.FruitShopOperation;
import core.basesyntax.service.interfaces.TransactionStrategy;
import core.basesyntax.service.transaction.TransactionHandler;
import core.basesyntax.service.transaction.impl.BalanceTransactionHandler;
import core.basesyntax.service.transaction.impl.PurchaseTransactionHandler;
import core.basesyntax.service.transaction.impl.ReturnTransactionHandler;
import core.basesyntax.service.transaction.impl.SupplyTransactionHandler;
import java.util.Map;

public class TransactionStrategyImpl implements TransactionStrategy {
    public static final Map<FruitShopOperation, TransactionHandler> handlers = Map.of(
            FruitShopOperation.BALANCE, new BalanceTransactionHandler(),
            FruitShopOperation.PURCHASE, new PurchaseTransactionHandler(),
            FruitShopOperation.SUPPLY, new SupplyTransactionHandler(),
            FruitShopOperation.RETURN, new ReturnTransactionHandler()
    );

    @Override
    public TransactionHandler get(FruitShopOperation fruitShopOperation) {
        return handlers.get(fruitShopOperation);
    }
}
