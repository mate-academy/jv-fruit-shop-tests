package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.ShopTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.ShopOperationHandler;
import java.util.List;
import java.util.Objects;

public class DefaultShopService implements ShopService {
    private final Storage storage;
    private final OperationStrategy operationStrategy;

    public DefaultShopService(Storage storage, OperationStrategy operationStrategy) {
        this.storage = Objects.requireNonNull(storage, "storage is null");
        this.operationStrategy = Objects.requireNonNull(
                operationStrategy, "operationStrategy is null");
    }

    @Override
    public void process(List<ShopTransaction> transactions) {
        Objects.requireNonNull(transactions, "Transactions list is null");

        for (ShopTransaction transaction : transactions) {
            if (transaction.quantity() < 0) {
                throw new RuntimeException("Transaction quantity must be greater than 0");
            }

            ShopOperationHandler handler = operationStrategy.resolve(transaction.operation());
            handler.handle(storage, transaction.product(), transaction.quantity());
        }
    }
}
