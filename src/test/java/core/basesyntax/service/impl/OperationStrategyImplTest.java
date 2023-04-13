package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static final FruitTransaction.Operation KEY = FruitTransaction.Operation.BALANCE;
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final OperationHandler operationHandler = new BalanceOperationHandler(fruitDao);
    private final OperationStrategy strategy = new OperationStrategyImpl();

    @Test
    void setStrategyWithNullValue_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> strategy.setStrategy(KEY, null));
    }

    @Test
    void setStrategy_Ok() {
        strategy.setStrategy(KEY, operationHandler);
        Assert.assertEquals(operationHandler, strategy.getStrategy(KEY));
    }
}
