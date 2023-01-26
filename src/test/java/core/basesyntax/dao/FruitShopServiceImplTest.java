package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.StoreOperation;
import core.basesyntax.strategy.StoreOperationStrategy;
import core.basesyntax.strategy.StoreOperationStrategyImpl;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static FruitShopService fruitShopService;
    private List<FruitTransaction> dataList = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        Map<StoreOperation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(StoreOperation.BALANCE, new BalanceOperationHandler());
        handlerMap.put(StoreOperation.SUPPLY, new SupplyOperationHandler());
        handlerMap.put(StoreOperation.PURCHASE, new PurchaseOperationHandler());
        handlerMap.put(StoreOperation.RETURN, new ReturnOperationHandler());
        StoreOperationStrategy operationStrategy = new StoreOperationStrategyImpl(handlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
    }

    @Before
    public void setUp() {
        dataList.add(new FruitTransaction(StoreOperation.BALANCE, "apple", 50));
        dataList.add(new FruitTransaction(StoreOperation.SUPPLY, "apple", 50));
        dataList.add(new FruitTransaction(StoreOperation.PURCHASE, "apple", 30));
        dataList.add(new FruitTransaction(StoreOperation.RETURN, "apple", 15));
    }

    @Test
    public void addValidDataToStorage_Ok() {
        fruitShopService.addDataToStorage(dataList);
        Integer actualResult = FruitStorage.storage.get("apple");
        Integer expectedResult = 85;
        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void addBalanceAmountTwice_NotOk() {
        dataList.add(new FruitTransaction(StoreOperation.BALANCE, "apple", 150));
        fruitShopService.addDataToStorage(dataList);
    }

    @Test(expected = RuntimeException.class)
    public void addNegativeBalanceAmount_NotOk() {
        dataList.add(new FruitTransaction(StoreOperation.BALANCE, "apple", -60));
        fruitShopService.addDataToStorage(dataList);
    }

    @Test(expected = RuntimeException.class)
    public void addNegativeSupplyAmount_NotOk() {
        dataList.add(new FruitTransaction(StoreOperation.SUPPLY, "apple", -30));
        fruitShopService.addDataToStorage(dataList);
    }

    @Test(expected = RuntimeException.class)
    public void addNegativePurchaseAmount_NotOk() {
        dataList.add(new FruitTransaction(StoreOperation.PURCHASE, "apple", -10));
        fruitShopService.addDataToStorage(dataList);
    }

    @Test(expected = RuntimeException.class)
    public void addPurchaseAmountLargerThanTotalAmount_NotOk() {
        dataList.add(new FruitTransaction(StoreOperation.PURCHASE, "apple", 200));
        fruitShopService.addDataToStorage(dataList);
    }

    @Test(expected = RuntimeException.class)
    public void addNegativeReturnAmount_NotOk() {
        dataList.add(new FruitTransaction(StoreOperation.RETURN, "apple", -40));
        fruitShopService.addDataToStorage(dataList);
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
