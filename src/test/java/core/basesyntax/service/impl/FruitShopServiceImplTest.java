package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.fruitscontent.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private FruitShopService fruitShopService;
    private OperationStrategy operationStrategy;

    @Before
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandlerImpl());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandlerImpl());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandlerImpl());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandlerImpl());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
    }

    @Test
    public void processOfOperations_GetCorrectData_Ok() {
        List<FruitTransaction> fruitTransactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 4),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 2),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10)
        );
        fruitShopService.processOfOperations(fruitTransactionList);
        int expected = 18;
        int actual = FruitStorage.fruits.get("apple");
        assertEquals(expected, actual);
    }

    @Test
    public void processOfOperations_CheckEmptyList_Ok() {
        List<FruitTransaction> emptyList = new ArrayList<>();
        fruitShopService.processOfOperations(emptyList);
        int expected = 0;
        int actual = FruitStorage.fruits.size();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruits.clear();
    }
}
