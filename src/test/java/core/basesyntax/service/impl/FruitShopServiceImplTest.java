package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
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
    private static List<FruitTransaction> fruitTransactions;
    private static Map<String,Integer> expectedFruitStorage;

    @BeforeClass
    public static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler()
        );
        fruitShopService = new FruitShopServiceImpl(new OperationStrategyImpl(operationHandlerMap));
        expectedFruitStorage = new HashMap<>();
        fruitTransactions = new ArrayList<>();

    }

    @Before
    public void init() {
        fruitTransactions = Stream.of(
                        new FruitTransaction(BALANCE,BANANA,20),
                        new FruitTransaction(BALANCE,APPLE,100),
                        new FruitTransaction(SUPPLY,BANANA,100),
                        new FruitTransaction(PURCHASE,BANANA,13),
                        new FruitTransaction(RETURN,APPLE,10),
                        new FruitTransaction(PURCHASE,APPLE,20),
                        new FruitTransaction(PURCHASE,BANANA,5),
                        new FruitTransaction(SUPPLY,BANANA,50))
                .collect(Collectors.toList());
    }

    @Test
    public void processData_ok() {
        expectedFruitStorage.put(BANANA,BANANA_AMOUNT);
        expectedFruitStorage.put(APPLE,APPLE_AMOUNT);
        fruitShopService.processData(fruitTransactions);
        assertEquals(expectedFruitStorage,Storage.fruitMap);
    }

    @Test (expected = RuntimeException.class)
    public void processData_notOk() {
        fruitShopService.processData(null);
    }

    @Test
    public void emptyData_ok() {
        List<FruitTransaction> emptyFruitTransactions = new ArrayList<>();
        fruitShopService.processData(emptyFruitTransactions);
        assertEquals(expectedFruitStorage,Storage.fruitMap);
    }

    @Test (expected = RuntimeException.class)
    public void incorrectData_notOk() {
        fruitTransactions.add(new FruitTransaction(null,BANANA,100));
        fruitShopService.processData(fruitTransactions);
    }

    @Test (expected = RuntimeException.class)
    public void negativeAmount_notOk() {
        fruitTransactions.add(new FruitTransaction(BALANCE,BANANA,-200));
        fruitShopService.processData(fruitTransactions);
    }

    @After
    public void tearDown() {
        Storage.fruitMap.clear();
        expectedFruitStorage.clear();
        fruitTransactions.clear();
    }
}
