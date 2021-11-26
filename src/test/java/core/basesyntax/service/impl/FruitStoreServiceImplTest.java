package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.FruitStoreService;
import core.basesyntax.strategy.Manipulation;
import core.basesyntax.strategy.ManipulationService;
import core.basesyntax.strategy.ManipulationStrategy;
import core.basesyntax.strategy.impl.BalanceManipulationService;
import core.basesyntax.strategy.impl.ManipulationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseManipulationService;
import core.basesyntax.strategy.impl.ReturnManipulationService;
import core.basesyntax.strategy.impl.SupplyManipulationService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitStoreServiceImplTest {
    private static FruitStorageDao fruitStorageDao;
    private static Map<Manipulation, ManipulationService> manipulationServiceMap;
    private static ManipulationStrategy manipulationStrategy;
    private static List<TransactionDto> transactionDtos;
    private static FruitStoreService fruitStoreService;

    @BeforeClass
    public static void beforeClass() {
        fruitStorageDao = new FruitStorageDaoImpl();
        transactionDtos = new ArrayList<>();
        manipulationServiceMap = new HashMap<>();
        manipulationServiceMap.put(Manipulation.BALANCE,
                new BalanceManipulationService(fruitStorageDao));
        manipulationServiceMap.put(Manipulation.PURCHASE,
                new PurchaseManipulationService(fruitStorageDao));
        manipulationServiceMap.put(Manipulation.RETURN,
                new ReturnManipulationService(fruitStorageDao));
        manipulationServiceMap.put(Manipulation.SUPPLY,
                new SupplyManipulationService(fruitStorageDao));
        manipulationStrategy = new ManipulationStrategyImpl(manipulationServiceMap);
        fruitStoreService = new FruitStoreServiceImpl(fruitStorageDao, manipulationStrategy);
    }

    @Before
    public void beforeEach() {
        transactionDtos.add(new TransactionDto("b", "banana", 65));
        transactionDtos.add(new TransactionDto("s", "banana", 5));
        transactionDtos.add(new TransactionDto("r", "banana", 5));
        transactionDtos.add(new TransactionDto("p", "banana", 10));
    }

    @Test
    public void changeBalance_allManipulation_ok() {
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("banana", 65));
        List<Fruit> actual = fruitStoreService.changeBalance(transactionDtos);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeBalance_purchaseManipulation_ok() {
        transactionDtos.add(new TransactionDto("p", "banana", 20));
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("banana", 45));
        List<Fruit> actual = fruitStoreService.changeBalance(transactionDtos);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void changeBalance_purchaseMoreThanBalance_notOk() {
        transactionDtos.add(new TransactionDto("p", "banana", 100));
        fruitStoreService.changeBalance(transactionDtos);
    }

    @Test
    public void changeBalance_returnManipulation_ok() {
        transactionDtos.add(new TransactionDto("r", "banana", 5));
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("banana", 70));
        List<Fruit> actual = fruitStoreService.changeBalance(transactionDtos);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void changeBalance_supplyManipulation_ok() {
        transactionDtos.add(new TransactionDto("s", "banana", 10));
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("banana", 75));
        List<Fruit> actual = fruitStoreService.changeBalance(transactionDtos);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEach() {
        FruitStorage.fruit.clear();
        transactionDtos.clear();
    }
}
