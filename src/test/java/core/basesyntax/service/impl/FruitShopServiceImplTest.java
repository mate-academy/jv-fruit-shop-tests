package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.BalanceOperationHandler;
import core.basesyntax.strategy.handler.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.impl.ReturnOperationHandler;
import core.basesyntax.strategy.handler.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {

    public static final String BANANA = "banana";
    public static final String APPLE = "apple";
    public static final FruitTransaction.Operation BALANCE = FruitTransaction.Operation.BALANCE;
    public static final FruitTransaction.Operation SUPPLY = FruitTransaction.Operation.SUPPLY;
    public static final FruitTransaction.Operation RETURN = FruitTransaction.Operation.RETURN;
    public static final FruitTransaction.Operation PURCHASE = FruitTransaction.Operation.PURCHASE;

    public static final Integer BANANA_AMOUNT = 152;
    public static final Integer APPLE_AMOUNT = 90;
    private static FruitShopService fruitShopService;
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation,OperationHandler> operationHandlerMap;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeClass
    public static void beforeAll() {

        fruitTransactions = new ArrayList<>();

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

        fruitTransactions.add(new FruitTransaction(BALANCE,BANANA,20));
        fruitTransactions.add(new FruitTransaction(BALANCE,APPLE,100));
        fruitTransactions.add(new FruitTransaction(SUPPLY,BANANA,100));
        fruitTransactions.add(new FruitTransaction(PURCHASE,BANANA,13));
        fruitTransactions.add(new FruitTransaction(RETURN,APPLE,10));
        fruitTransactions.add(new FruitTransaction(PURCHASE,APPLE,20));
        fruitTransactions.add(new FruitTransaction(PURCHASE,BANANA,5));
        fruitTransactions.add(new FruitTransaction(SUPPLY,BANANA,50));
    }

    @Before
    public void init() {
        Storage.fruitMap.clear();
    }

    @Test
    public void processData_ok() {
        Map<String,Integer> expectedFruitStorage = new HashMap<>();
        expectedFruitStorage.put(BANANA,BANANA_AMOUNT);
        expectedFruitStorage.put(APPLE,APPLE_AMOUNT);

        fruitShopService.processData(fruitTransactions);

        assertEquals(expectedFruitStorage,Storage.fruitMap);
    }

    @Test (expected = RuntimeException.class)
    public void processData_notOk() {
        fruitShopService.processData(null);
    }
}
