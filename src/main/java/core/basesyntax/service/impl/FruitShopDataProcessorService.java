package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataProcessorService;
import core.basesyntax.strategy.ShopOperationStrategy;
import java.util.List;

public class FruitShopDataProcessorService implements DataProcessorService {
    private final ShopOperationStrategy shopOperationStrategy;

    public FruitShopDataProcessorService(ShopOperationStrategy shopOperationStrategy) {
        this.shopOperationStrategy = shopOperationStrategy;
    }

    @Override
    public void processData(List<FruitTransaction> data) {
        for (FruitTransaction transaction : data) {
            shopOperationStrategy.get(transaction.getOperation()).handle(transaction);
        }
    }
}
