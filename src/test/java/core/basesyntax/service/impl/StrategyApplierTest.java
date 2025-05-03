package core.basesyntax.service.impl;

import core.basesyntax.db.DaoServiceHashMap;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.SaveStrategy;
import core.basesyntax.strategy.impl.SaveStrategyBalance;
import core.basesyntax.strategy.impl.SaveStrategyPurchase;
import core.basesyntax.strategy.impl.SaveStrategySupply;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyApplierTest {
    private static final String DEFAULT_FRUIT = "apple";
    private static final String ADDITIONAL_FRUIT = "banana";
    private static final List<FruitTransaction> validTransactionsDefault = new ArrayList<>();
    private static final List<FruitTransaction> validTransactionsAdditional = new ArrayList<>();
    private static DaoServiceHashMap storage;
    private static Map<String, Integer> expectedResult;
    private static final Map<Operation, SaveStrategy> STRATEGIES_WITHOUT_RETURN =
            new HashMap<>();
    private static StrategySelector strategySelector;
    private static StrategyApplier strategyApplier;

    @BeforeClass
    public static void initialize() {
        storage = new DaoServiceHashMap();
        expectedResult = new HashMap<>();
        STRATEGIES_WITHOUT_RETURN.put(Operation.SUPPLY, new SaveStrategySupply(storage));
        STRATEGIES_WITHOUT_RETURN.put(Operation.PURCHASE, new SaveStrategyPurchase(storage));
        STRATEGIES_WITHOUT_RETURN.put(Operation.BALANCE, new SaveStrategyBalance(storage));
        strategySelector = new StrategySelector(STRATEGIES_WITHOUT_RETURN);
        strategyApplier = new StrategyApplier(strategySelector);
        validTransactionsDefault.add(
                new FruitTransaction(
                        Operation.BALANCE.getCode(),
                        DEFAULT_FRUIT,
                        100));
        validTransactionsDefault.add(
                new FruitTransaction(
                        Operation.PURCHASE.getCode(),
                        DEFAULT_FRUIT,
                        50));
        validTransactionsDefault.add(
                new FruitTransaction(
                        Operation.SUPPLY.getCode(),
                        DEFAULT_FRUIT,
                        20));
        validTransactionsAdditional.add(
                new FruitTransaction(
                        Operation.BALANCE.getCode(),
                        ADDITIONAL_FRUIT,
                        100));
        validTransactionsAdditional.add(
                new FruitTransaction(
                        Operation.PURCHASE.getCode(),
                        ADDITIONAL_FRUIT,
                        50));
        validTransactionsAdditional.add(
                new FruitTransaction(
                        Operation.SUPPLY.getCode(),
                        ADDITIONAL_FRUIT,
                        20));
    }

    @Before
    public void reset() {
        expectedResult.clear();
        storage.getMemory().clear();
    }

    @Test
    public void applyAll_validSingleFruit_ok() {
        expectedResult.put(DEFAULT_FRUIT, 70);
        strategyApplier.applyAll(validTransactionsDefault);
        Assert.assertEquals(expectedResult, storage.getMemory());
    }

    @Test
    public void applyAll_validTwoFruits_ok() {
        expectedResult.put(DEFAULT_FRUIT, 70);
        expectedResult.put(ADDITIONAL_FRUIT, 70);
        for (int i = 0; i < validTransactionsDefault.size(); i++) {
            strategyApplier.applyAll(List.of(validTransactionsDefault.get(i)));
            strategyApplier.applyAll(List.of(validTransactionsAdditional.get(i)));
        }
        Assert.assertEquals(expectedResult, storage.getMemory());
    }

    @Test
    public void applyAll_emptyList_ok() {
        strategyApplier.applyAll(List.of());
        Assert.assertEquals(expectedResult, storage.getMemory());
    }

    @Test(expected = NullPointerException.class)
    public void applyAll_nullList_notOk() {
        strategyApplier.applyAll(null);
    }
}
