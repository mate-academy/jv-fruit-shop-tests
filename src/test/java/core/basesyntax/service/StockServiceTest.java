package core.basesyntax.service;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.StockServiceImpl;
import core.basesyntax.strategy.ActivityStrategy;
import core.basesyntax.strategy.impl.ActivityStrategyImpl;
import core.basesyntax.strategy.impl.BalanceActivity;
import core.basesyntax.strategy.impl.PurchaseActivity;
import core.basesyntax.strategy.impl.ReturnActivity;
import core.basesyntax.strategy.impl.SupplyActivity;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StockServiceTest {
    private static final int CALCULATE_STOCK_RESULT = 121;
    private static final String BANANA = "banana";
    private static ActivityStrategy strategy;
    private StockService stockService = new StockServiceImpl(strategy);
    private List<FruitTransaction> fruitTransactions;

    @BeforeAll
    static void beforeAll() {
        strategy = new ActivityStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, new BalanceActivity(),
                        FruitTransaction.Operation.PURCHASE, new PurchaseActivity(),
                        FruitTransaction.Operation.SUPPLY, new SupplyActivity(),
                        FruitTransaction.Operation.RETURN, new ReturnActivity()));
    }

    @Test
    void calculateStock_Ok() {
        fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 40),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 60),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 1));
        stockService.calculateStock(fruitTransactions);
        int actual = storage.get(BANANA);
        assertEquals(CALCULATE_STOCK_RESULT, actual);
    }

    @Test
    void calculateStockWithEmptyList_ok() {
        stockService.calculateStock(Collections.emptyList());
        assertTrue(storage.isEmpty());
    }

    @Test
    void calculateStockWithNull_ok() {
        assertThrows(NullPointerException.class, () -> {
            stockService.calculateStock(null);
        });
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }
}
