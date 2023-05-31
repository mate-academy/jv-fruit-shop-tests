package service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Fruit;
import model.FruitTransaction;
import model.Operation;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FruitShopService;
import service.OperationService;
import storage.Storage;
import strategy.BalanceOperationHandlerImpl;
import strategy.OperationHandler;
import strategy.PurchaseOperationHandlerImpl;
import strategy.ReturnOperationHandlerImpl;
import strategy.SupplyOperationHandlerImpl;

public class FruitShopServiceImplTest {
    private static List<FruitTransaction> fruitRecordList;
    private static FruitShopService fruitShopService;

    @BeforeClass
    public static void beforeClass() {
        Map<Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(Operation.BALANCE, new BalanceOperationHandlerImpl());
        handlerMap.put(Operation.SUPPLY, new SupplyOperationHandlerImpl());
        handlerMap.put(Operation.PURCHASE, new PurchaseOperationHandlerImpl());
        handlerMap.put(Operation.RETURN, new ReturnOperationHandlerImpl());
        OperationService operationService = new OperationServiceImpl(handlerMap);
        fruitShopService = new FruitShopServiceImpl(operationService);
        fruitRecordList = new ArrayList<>();
        fruitRecordList.add(new FruitTransaction("b", new Fruit("banana"), 200));
        fruitRecordList.add(new FruitTransaction("b", new Fruit("apple"), 20));
        fruitRecordList.add(new FruitTransaction("s", new Fruit("banana"), 20));
        fruitRecordList.add(new FruitTransaction("s", new Fruit("apple"), 20));
        fruitRecordList.add(new FruitTransaction("p", new Fruit("apple"), 10));
        fruitRecordList.add(new FruitTransaction("p", new Fruit("banana"), 10));
        fruitRecordList.add(new FruitTransaction("r", new Fruit("banana"), 10));
        fruitRecordList.add(new FruitTransaction("r", new Fruit("apple"), 5));
    }

    @Test
    public void process_correctAmount_isOk() {
        int expected = 220;
        fruitShopService.process(fruitRecordList);
        int actual = Storage.storage.get(new Fruit("banana"));
        assertEquals("Must be equal",expected, actual);
    }
}
