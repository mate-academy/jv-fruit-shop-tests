package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.exception.FruitException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationStrategy;
import core.basesyntax.strategy.impl.PurchaseOperationStrategy;
import core.basesyntax.strategy.impl.ReturnOperationStrategy;
import core.basesyntax.strategy.impl.SupplyOperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FactoryStrategyTest {
    private static Map<FruitTransaction.Operation, OperationStrategy> correctStrategies;
    private static Map<FruitTransaction.Operation, OperationStrategy> incorrectStrategies;
    private static FruitDao fruitDao;
    private static FactoryStrategy factoryStrategy;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        correctStrategies = new HashMap<>();
        correctStrategies.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationStrategy(fruitDao));
        correctStrategies.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationStrategy(fruitDao));
        correctStrategies.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationStrategy(fruitDao));
        correctStrategies.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationStrategy(fruitDao));
        incorrectStrategies = new HashMap<>();
        incorrectStrategies.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationStrategy(fruitDao));
        incorrectStrategies.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationStrategy(fruitDao));
        incorrectStrategies.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationStrategy(fruitDao));
    }

    @Test
    public void getOperationStrategy_Ok() {
        factoryStrategy = new FactoryStrategy(correctStrategies);
        OperationStrategy expectedStrategy = new PurchaseOperationStrategy(fruitDao);
        OperationStrategy actualStrategy = factoryStrategy
                .getOperationStrategy(FruitTransaction.Operation.PURCHASE);
        assertEquals(expectedStrategy.getClass(), actualStrategy.getClass());
    }

    @Test (expected = FruitException.class)
    public void factoryStrategy_NotOk_incorrectMapStrategies() {
        factoryStrategy = new FactoryStrategy(incorrectStrategies);
    }
}
