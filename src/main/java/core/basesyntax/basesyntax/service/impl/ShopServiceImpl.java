package core.basesyntax.basesyntax.service.impl;

import core.basesyntax.basesyntax.db.StorageImpl;
import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.service.OperationHandlerProvider;
import core.basesyntax.basesyntax.service.ShopService;
import core.basesyntax.basesyntax.strategy.OperationHandler;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationHandlerProvider provider;
    private final StorageImpl storage;

    public ShopServiceImpl(OperationHandlerProvider provider, StorageImpl storage) {
        this.provider = provider;
        this.storage = storage;
    }

    @Override
    public StorageImpl process(List<FruitTransaction> transactions) {
        if (transactions == null) {
            throw new RuntimeException("Transactions cannot be null");
        }

        for (FruitTransaction tx : transactions) {
            OperationHandler handler = provider.get(tx.getOperation());
            handler.apply(tx.getFruit(), tx.getQuantity(), storage);
        }
        return storage;
    }
}
