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
    public static final Map<LocalDate, Map<Fruit, Integer>> fruitStorage = new HashMap<>();
    public static final Map<Fruit, Integer> fruitMap = new HashMap<>();
    private static FruitStorageRepository repository;
    private static FruitStorageServiceImpl service;

    @BeforeClass
    public static void initialize() {
        repository = new FruitStorageRepositoryImpl();
        service = new FruitStorageServiceImpl();
    }

    @After
    public void clear() {
        fruitMap.clear();
        fruitStorage.clear();
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
    public void addToStorage_emptyMap() {
        service.addToStorage(fruitMap);
        Assert.assertTrue("Expected a empty storage with empty input, "
                + "but was:" + fruitStorage, fruitStorage.isEmpty());
    }

    @Test
    public void getFromStorage_validData_Ok() {
        fruitMap.put(Fruit.builder().name("peach").build(), 16);
        fruitMap.put(Fruit.builder().name("banana").build(), 13);
        repository.add(fruitMap);
        Map<Fruit, Integer> expected = fruitMap;
        Map<Fruit, Integer> actual = service.getFromStorage(LocalDate.now());
        Assert.assertEquals("Expected: " + expected
                + " with map: " + fruitMap + ", but was: " + actual, expected, actual);
    }

    @Test
    public void getFromStorage_nullInput_OK() {
        service.addToStorage(null);
        Map<Fruit, Integer> expected = null;
        Map<Fruit, Integer> actual = service.getFromStorage(LocalDate.now());
        Assert.assertEquals("Expected a null map, but was: "
                + actual, expected, actual);
    }

    @Test
    public void getFromStorage_emptyInput_Ok() {
        service.addToStorage(fruitMap);
        Map<Fruit, Integer> expected = new HashMap<>();
        Map<Fruit, Integer> actual = service.getFromStorage(LocalDate.now());
        Assert.assertEquals("Expected a empty map, but was: "
                + actual, expected, actual);
    }
}
