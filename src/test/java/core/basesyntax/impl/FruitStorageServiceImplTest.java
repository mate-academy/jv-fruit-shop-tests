package core.basesyntax.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.repository.FruitStorageRepository;
import core.basesyntax.repository.FruitStorageRepositoryImpl;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitStorageServiceImplTest {
    private static Map<LocalDate, Map<Fruit, Integer>> fruitStorage;
    private static Map<Fruit, Integer> fruitMap;
    private static FruitStorageRepository repository;
    private static FruitStorageServiceImpl service;
    private static Map<Fruit, Integer> expected;
    private static Map<Fruit, Integer> actual;

    @BeforeClass
    public static void initialize() {
        repository = new FruitStorageRepositoryImpl();
        service = new FruitStorageServiceImpl();
        fruitStorage = new HashMap<>();
        fruitMap = new HashMap<>();
        expected = new HashMap<>();
        actual = new HashMap<>();
    }

    @After
    public void clear() {
        fruitMap.clear();
        fruitStorage.clear();
        expected = new HashMap<>();
        actual = new HashMap<>();
    }

    @Test
    public void addToStorage_rightInput_Ok() {
        fruitMap.put(Fruit.builder().name("peach").build(), 16);
        fruitMap.put(Fruit.builder().name("banana").build(), 13);
        fruitStorage.put(LocalDate.now(), fruitMap);
        Map<LocalDate, Map<Fruit, Integer>> expected = fruitStorage;
        service.addToStorage(fruitMap);
        Assert.assertEquals("Expected map with peach and banana, but was: "
                + fruitStorage, expected, fruitStorage);
    }

    @Test
    public void addToStorage_nullInput_Ok() {
        service.addToStorage(null);
        Assert.assertTrue("Expected a empty storage with null input"
                + ", but was: " + fruitStorage, fruitStorage.isEmpty());
    }

    @Test
    public void addToStorage_emptyMap_Ok() {
        service.addToStorage(fruitMap);
        Assert.assertTrue("Expected a empty storage with empty input, "
                + "but was:" + fruitStorage, fruitStorage.isEmpty());
    }

    @Test
    public void getFromStorage_validData_Ok() {
        fruitMap.put(Fruit.builder().name("peach").build(), 16);
        fruitMap.put(Fruit.builder().name("banana").build(), 13);
        repository.add(fruitMap);
        expected = fruitMap;
        actual = service.getFromStorage(LocalDate.now());
        Assert.assertEquals("Expected: " + expected
                + " with map: " + fruitMap + ", but was: " + actual, expected, actual);
    }

    @Test
    public void getFromStorage_nullInput_OK() {
        service.addToStorage(null);
        expected = null;
        actual = service.getFromStorage(LocalDate.now());
        Assert.assertEquals("Expected a null map, but was: "
                + actual, expected, actual);
    }

    @Test
    public void getFromStorage_emptyInput_Ok() {
        service.addToStorage(fruitMap);
        actual = service.getFromStorage(LocalDate.now());
        Assert.assertEquals("Expected a empty map, but was: "
                + actual, expected, actual);
    }
}
