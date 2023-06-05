package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handlerimpl.BalanceOperation;
import core.basesyntax.strategy.handlerimpl.PurchaseOperation;
import core.basesyntax.strategy.handlerimpl.ReturnOperation;
import core.basesyntax.strategy.handlerimpl.SupplyOperation;
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
import org.junit.jupiter.api.Assertions;

public class FruitShopServiceImplTest {
    public static final Integer BANANA_AMOUNT = 152;
    public static final Integer APPLE_AMOUNT = 90;
    private static FruitShopService fruitShopService;
    private static List<FruitTransaction> fruitTransactions;
    private static Map<String,Integer> expectedFruitStorage;

    @BeforeClass
    public static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation()
        );
        fruitShopService = new FruitShopServiceImpl(new OperationStrategyImpl(operationHandlerMap));
        expectedFruitStorage = new HashMap<>();
        fruitTransactions = new ArrayList<>();
    }

    @Before
    public void init() {
        fruitTransactions = Stream.of(
                        new FruitTransaction(FruitTransaction.Operation.BALANCE,
                                "banana", 20),
                        new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                                "apple", 100),
                        new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                                "banana", 100),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                "banana", 13),
                        new FruitTransaction(FruitTransaction.Operation.RETURN,
                                "apple", 10),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                "apple", 20),
                        new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                                "banana", 5),
                        new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                                "banana", 50))
                .collect(Collectors.toList());
    }

    @Test
    public void processData_ok() {
        expectedFruitStorage.put("banana", BANANA_AMOUNT);
        expectedFruitStorage.put("apple", APPLE_AMOUNT);
        fruitShopService.process(fruitTransactions);
        Assertions.assertEquals(expectedFruitStorage, Storage.fruitStorage);
    }

    @Test (expected = RuntimeException.class)
    public void processData_notOk() {
        fruitShopService.process(null);
    }

    @Test
    public void emptyData_ok() {
        List<FruitTransaction> emptyFruitTransactions = new ArrayList<>();
        fruitShopService.process(emptyFruitTransactions);
        Assertions.assertEquals(expectedFruitStorage, Storage.fruitStorage);
    }

    @Test (expected = RuntimeException.class)
    public void incorrectData_notOk() {
        fruitTransactions.add(new FruitTransaction(null, "banana", 100));
        fruitShopService.process(fruitTransactions);
    }

    @Test (expected = RuntimeException.class)
    public void negativeAmount_notOk() {
        fruitTransactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", -200));
        fruitShopService.process(fruitTransactions);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
        expectedFruitStorage.clear();
        fruitTransactions.clear();
    }
}
