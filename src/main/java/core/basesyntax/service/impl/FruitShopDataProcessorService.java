package core.basesyntax.service.impl;

import core.basesyntax.model.*;
import core.basesyntax.service.*;
import core.basesyntax.strategy.*;

import java.util.*;

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
