package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.BalanceHandlerImpl;
import core.basesyntax.service.operation.Handler;
import core.basesyntax.service.operation.PurchaseHandlerImpl;
import core.basesyntax.service.operation.ReturnHandlerImpl;
import core.basesyntax.service.operation.SupplyHandlerImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static List<FruitRecord> fruitRecordList;
    private static FruitShopService fruitShopService;
    private int expected;
    private int actual;

    @BeforeClass
    public static void beforeClass() {
        Map<Operation, Handler> handlerMap = new HashMap<>();
        handlerMap.put(Operation.BALANCE, new BalanceHandlerImpl());
        handlerMap.put(Operation.SUPPLY, new SupplyHandlerImpl());
        handlerMap.put(Operation.PURCHASE, new PurchaseHandlerImpl());
        handlerMap.put(Operation.RETURN, new ReturnHandlerImpl());
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
        fruitRecordList = new ArrayList<>();
        fruitRecordList.add(new FruitRecord("b", new Fruit("banana"), 200));
        fruitRecordList.add(new FruitRecord("b", new Fruit("apple"), 20));
        fruitRecordList.add(new FruitRecord("s", new Fruit("banana"), 20));
        fruitRecordList.add(new FruitRecord("s", new Fruit("apple"), 20));
        fruitRecordList.add(new FruitRecord("p", new Fruit("apple"), 10));
        fruitRecordList.add(new FruitRecord("p", new Fruit("banana"), 10));
        fruitRecordList.add(new FruitRecord("r", new Fruit("banana"), 10));
        fruitRecordList.add(new FruitRecord("r", new Fruit("apple"), 5));
    }

    @Test
    public void transfer_correctAmount_ok() {
        expected = 220;
        fruitShopService.transfer(fruitRecordList);
        actual = Storage.storage.get(new Fruit("banana"));
        assertEquals(expected, actual);
        expected = 35;
        fruitShopService.transfer(fruitRecordList);
        actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
