package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.OperationStrategy;
import core.basesyntax.service.operation.OperationStrategyImpl;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static FruitDao fruitDao;
    private static OperationStrategy operationStrategy;
    private static FruitShopService fruitShopService;
    private static Map<FruitRecordDto.Type, OperationHandler> operationTypesMap;
    private static Fruit banana;
    private static Fruit apple;
    private static FruitRecordDto fruitRecordDto1;
    private static FruitRecordDto fruitRecordDto2;
    private static Map<Fruit, Integer> expected;
    private static Map<Fruit, Integer> actual;

    @BeforeClass
    public static void setUpBeforeAll() {
        fruitDao = new FruitDaoImpl();
        operationTypesMap = new HashMap<>();
        operationTypesMap.put(FruitRecordDto.Type.BALANCE, new BalanceOperationHandler(fruitDao));
        operationTypesMap.put(FruitRecordDto.Type.PURCHASE, new PurchaseOperationHandler(fruitDao));
        operationTypesMap.put(FruitRecordDto.Type.SUPPLY, new SupplyOperationHandler(fruitDao));
        operationTypesMap.put(FruitRecordDto.Type.RETURN, new ReturnOperationHandler(fruitDao));
        operationStrategy = new OperationStrategyImpl(operationTypesMap);
        fruitShopService = new FruitShopServiceImpl(fruitDao, operationStrategy);
        banana = new Fruit("banana");
        apple = new Fruit("apple");
        fruitRecordDto2 = new FruitRecordDto(FruitRecordDto.Type.BALANCE, apple, 100);
    }

    @Before
    public void setUp() {
        fruitRecordDto1 = new FruitRecordDto(FruitRecordDto.Type.BALANCE, banana, 20);
    }

    @Test
    public void updateStorage_inputValidData_Ok() {
        Storage.fruits.put(banana, 20);
        Storage.fruits.put(apple,100);
        expected = Storage.fruits;
        fruitShopService.updateStorage(List.of(fruitRecordDto1, fruitRecordDto2));
        assertEquals(expected, Storage.fruits);
        expected = Storage.fruits;
        expected.put(banana, 33);
        fruitRecordDto1.setTypeOfOperation(FruitRecordDto.Type.SUPPLY);
        fruitRecordDto1.setAmount(13);
        fruitShopService.updateStorage(List.of(fruitRecordDto1));
        assertEquals(expected, Storage.fruits);
        expected.put(apple, 83);
        fruitRecordDto2.setTypeOfOperation(FruitRecordDto.Type.PURCHASE);
        fruitRecordDto2.setAmount(17);
        fruitShopService.updateStorage(List.of(fruitRecordDto2));
        assertEquals(expected, Storage.fruits);
        expected.put(banana, 43);
        fruitRecordDto1.setTypeOfOperation(FruitRecordDto.Type.RETURN);
        fruitRecordDto1.setAmount(10);
        assertEquals(expected, Storage.fruits);
    }

    @Test
    public void updateStorage_inputEmptyList_Ok() {
        expected = Storage.fruits;
        fruitShopService.updateStorage(new ArrayList<>());
        actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void updateStorage_inputNull_NotOk() {
        fruitShopService.updateStorage(null);
    }

    @Test (expected = RuntimeException.class)
    public void updateStorage_inputInvalidData_NotOk() {
        fruitShopService.updateStorage(List.of(fruitRecordDto1));
        fruitRecordDto1.setTypeOfOperation(FruitRecordDto.Type.PURCHASE);
        fruitRecordDto1.setAmount(30);
        fruitShopService.updateStorage(List.of(fruitRecordDto1));
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
