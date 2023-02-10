package core.basesyntax.service;

import core.basesyntax.strategy.OperationStrategy;

public class FruitShopServiceImplTest {
    OperationStrategy operationStrategy;
    private FruitShopService fruitShopService = new FruitShopServiceImpl
            (operationStrategy);


}