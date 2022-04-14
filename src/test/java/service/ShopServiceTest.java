package service;

import static org.junit.Assert.assertEquals;

import dao.FruitDao;
import dao.FruitDaoImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Fruit;
import model.FruitDataDto;
import org.junit.BeforeClass;
import org.junit.Test;
import service.actions.ActivityHandler;
import service.actions.Balance;
import service.actions.Purchase;
import service.actions.SupplyOrReturn;
import service.util.DataValidator;
import service.util.DataValidatorImpl;

public class ShopServiceTest {
    private static FruitDao fruitDao;
    private static DataValidator dataValidator;
    private static ShopService shopService;

    @BeforeClass
    public static void beforeClass() {
        dataValidator = new DataValidatorImpl();
        fruitDao = new FruitDaoImpl();
        fruitDao.getDB().clear();
        Map<String, ActivityHandler> activityHandlerMap = new HashMap<>();
        activityHandlerMap.put("BalanceHandler", new Balance(fruitDao));
        activityHandlerMap.put("PurchaseHandler", new Purchase(fruitDao, dataValidator));
        activityHandlerMap.put("SupplyOrReturn", new SupplyOrReturn(fruitDao));
        ActivityStrategy activityStrategy = new ActivityStrategyImpl(activityHandlerMap);
        shopService = new ShopServiceImpl(activityStrategy);
    }

    @Test
    public void fillStorage_NormalData_OK() {
        List<FruitDataDto> normalDtoList = List.of(
                new FruitDataDto("BalanceHandler","fruit1",10),
                new FruitDataDto("BalanceHandler","fruit2",20),
                new FruitDataDto("BalanceHandler","fruit3",30),
                new FruitDataDto("SupplyOrReturn", "fruit1", 20),
                new FruitDataDto("PurchaseHandler", "fruit2", 10));
        shopService.fillStorage(normalDtoList);
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("fruit1"), 30);
        expected.put(new Fruit("fruit2"), 10);
        expected.put(new Fruit("fruit3"), 30);
        Map<Fruit, Integer> actual = fruitDao.getDB();
        assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void fillStorage_WrongData_NotOk() {
        List<FruitDataDto> normalDtoList = List.of(
                new FruitDataDto("BalanceHandler", "fruit1", 10),
                new FruitDataDto("BalanceHandler", "fruit2", 20),
                new FruitDataDto("PurchaseHandler", "fruit2", 30));
        shopService.fillStorage(normalDtoList);
        fruitDao.getDB();
    }
}
