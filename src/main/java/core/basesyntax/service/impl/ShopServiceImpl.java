package core.basesyntax.service.impl;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopServiceImpl implements ShopService {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationHandlers = new HashMap<>();
        this.operationHandlers
                .put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        this.operationHandlers
                .put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        this.operationHandlers
                .put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        this.operationHandlers
                .put(FruitTransaction.Operation.RETURN, new ReturnOperation());
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        ShopStorage storage = ShopStorage.getInstance();
        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = operationHandlers
                    .get(transaction.getOperation());
            if (handler != null) {
                handler.handle(storage, transaction);
            } else {
                throw new RuntimeException("Operation not supported: "
                        + transaction.getOperation());
            }
        }
    }
}

