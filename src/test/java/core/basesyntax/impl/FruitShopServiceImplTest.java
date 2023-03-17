package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.FruitShopServiceImpl;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.StrategyImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static final List<FruitTransaction> EMPTY_LIST_TRANSACTION = new ArrayList<>();
    private static final List<FruitTransaction> VALID_LIST_TRANSACTION = new ArrayList<>();
    private static final List<FruitTransaction> NULL_LIST_TRANSACTION = null;
    private static final Strategy strategy = new StrategyImpl();
    private static FruitShopService fruitShopService;

    @BeforeClass
    public static void beforeAll() {
        fruitShopService = new FruitShopServiceImpl(strategy);
        VALID_LIST_TRANSACTION.add(new FruitTransaction("b", "banana", 23));
        VALID_LIST_TRANSACTION.add(new FruitTransaction("s", "banana", 100));
        VALID_LIST_TRANSACTION.add(new FruitTransaction("r", "banana", 15));
        VALID_LIST_TRANSACTION.add(new FruitTransaction("p", "banana", 23));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test (expected = RuntimeException.class)
    public void realizePattern_emptyListTransaction_notOk() {
        fruitShopService.realizePattern(EMPTY_LIST_TRANSACTION);
    }

    @Test
    public void realizePattern_validListTransaction_ok() {
        fruitShopService.realizePattern(VALID_LIST_TRANSACTION);
        int actual = Storage.storage.get("banana");
        assertEquals(115, actual);
    }

    @Test(expected = RuntimeException.class)
    public void realizePattern_nullListTransaction_notOk() {
        fruitShopService.realizePattern(NULL_LIST_TRANSACTION);
    }
}
