package core.basesyntax.service.impl;

import core.basesyntax.db.DaoService;
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
    private static final List<FruitTransaction> VALID_DEFAULT_TRANSACTIONS = new ArrayList<>();
    private static final List<FruitTransaction> VALID_ADDITIONAL_TRANSACTIONS = new ArrayList<>();
    private static DaoService STORAGE;
    private static final Map<Operation, SaveStrategy> STRATEGIES_WITHOUT_RETURN =
            new HashMap<>();
    private static StrategySelector strategySelector;
    private static StrategyApplier strategyApplier;

    @BeforeClass
    public static void initialize() {
        STORAGE = new DaoServiceHashMap();
        STRATEGIES_WITHOUT_RETURN.put(Operation.SUPPLY, new SaveStrategySupply(STORAGE));
        STRATEGIES_WITHOUT_RETURN.put(Operation.PURCHASE, new SaveStrategyPurchase(STORAGE));
        STRATEGIES_WITHOUT_RETURN.put(Operation.BALANCE, new SaveStrategyBalance(STORAGE));
        strategySelector = new StrategySelector(STRATEGIES_WITHOUT_RETURN);
        strategyApplier = new StrategyApplier(strategySelector);
        VALID_DEFAULT_TRANSACTIONS.add(
                new FruitTransaction(
                        Operation.BALANCE.getCode(),
                        DEFAULT_FRUIT,
                        100));
        VALID_DEFAULT_TRANSACTIONS.add(
                new FruitTransaction(
                        Operation.PURCHASE.getCode(),
                        DEFAULT_FRUIT,
                        50));
        VALID_DEFAULT_TRANSACTIONS.add(
                new FruitTransaction(
                        Operation.SUPPLY.getCode(),
                        DEFAULT_FRUIT,
                        20));
        VALID_ADDITIONAL_TRANSACTIONS.add(
                new FruitTransaction(
                        Operation.BALANCE.getCode(),
                        ADDITIONAL_FRUIT,
                        100));
        VALID_ADDITIONAL_TRANSACTIONS.add(
                new FruitTransaction(
                        Operation.PURCHASE.getCode(),
                        ADDITIONAL_FRUIT,
                        50));
        VALID_ADDITIONAL_TRANSACTIONS.add(
                new FruitTransaction(
                        Operation.SUPPLY.getCode(),
                        ADDITIONAL_FRUIT,
                        20));
    }

    @Before
    public void reset() {
        STORAGE.clear();
    }

    @Test
    public void applyAll_validSingleFruit_ok() {
        DaoService expectedStorage = new DaoServiceHashMap();
        expectedStorage.create(DEFAULT_FRUIT, 70);
        strategyApplier.applyAll(VALID_DEFAULT_TRANSACTIONS);
        Assert.assertEquals(STORAGE.getAll(), expectedStorage.getAll());
    }

    @Test
    public void applyAll_validTwoFruits_ok() {
        DaoService expectedStorage = new DaoServiceHashMap();
        expectedStorage.create(DEFAULT_FRUIT, 70);
        expectedStorage.create(ADDITIONAL_FRUIT, 70);
        for (int i = 0; i < VALID_DEFAULT_TRANSACTIONS.size(); i++) {
            strategyApplier.applyAll(List.of(VALID_DEFAULT_TRANSACTIONS.get(i)));
            strategyApplier.applyAll(List.of(VALID_ADDITIONAL_TRANSACTIONS.get(i)));
        }
        Assert.assertEquals(STORAGE.getAll(), expectedStorage.getAll());
    }

    @Test
    public void applyAll_emptyList_ok() {
        strategyApplier.applyAll(List.of());
    }

    @Test
    public void applyAll_nullList_notOk() {
        try {
            strategyApplier.applyAll(null);
            Assert.fail();
        } catch (RuntimeException e) {
            Assert.assertEquals(e.getClass(),
                    NullPointerException.class);
        }
    }
}
