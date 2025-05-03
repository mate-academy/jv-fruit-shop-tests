package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private OperationStrategy operationStrategy;
    private FruitShopService fruitShopService;

    @Before
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
    }

    @Test
    public void processTransactions_ok() {
        List<FruitTransaction> fruitTransactions = List.of(new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, "orange", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 60),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 50));
        fruitShopService.processTransactions(fruitTransactions);
        int expected = 95;
        int actual = FruitStorage.fruits.get("orange");
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruits.clear();
    }
}
