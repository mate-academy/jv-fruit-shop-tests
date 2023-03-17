package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.StrategyImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private static final List<FruitTransaction> EMPTY_LIST_TRANSACTION = new ArrayList<>();
    private static final List<FruitTransaction> VALID_LIST_TRANSACTION = new ArrayList<>();
    private static final List<FruitTransaction> NULL_LIST_TRANSACTION = null;
    private final Strategy strategy = new StrategyImpl();
    private final FruitShopService fruitShopService = new FruitShopServiceImpl(strategy);

    @BeforeAll
    static void beforeAll() {
        VALID_LIST_TRANSACTION.add(new FruitTransaction("b", "banana", 23));
        VALID_LIST_TRANSACTION.add(new FruitTransaction("s", "banana", 100));
        VALID_LIST_TRANSACTION.add(new FruitTransaction("r", "banana", 15));
        VALID_LIST_TRANSACTION.add(new FruitTransaction("p", "banana", 23));
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void emptyListTransaction_notOk() {
        assertThrows(RuntimeException.class, () ->
                fruitShopService.realizePattern(EMPTY_LIST_TRANSACTION));

    }

    @Test
    void validListTransaction_ok() {
        int expected = 115;
        fruitShopService.realizePattern(VALID_LIST_TRANSACTION);
        int actual = Storage.storage.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    void nullListTransaction_notOk() {
        assertThrows(RuntimeException.class, () ->
                fruitShopService.realizePattern(NULL_LIST_TRANSACTION));
    }
}
