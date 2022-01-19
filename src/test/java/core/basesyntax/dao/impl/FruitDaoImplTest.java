package core.basesyntax.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FruitDaoImplTest {
    private final Map<Fruit, Integer> getFruitMap = getGetFruitMap();
    private final Map<Fruit, Integer> putFruitMap = new HashMap<>();
    private final FruitDaoImpl getFruitDaoImpl = new FruitDaoImpl(getFruitMap);
    private final FruitDaoImpl putFruitDaoImpl = new FruitDaoImpl(putFruitMap);
    private final Fruit notExistFruit = new Fruit("Fruit isn't exist");

    @After
    public void tearDown() throws Exception {
        putFruitMap.clear();
    }

    @Test
    public void get_Ok() {
        for(Map.Entry<Fruit, Integer> entry : getFruitMap.entrySet()) {
            int expected = entry.getValue();
            int actual = getFruitDaoImpl.get(entry.getKey()).get();
            assertEquals("get(" + entry.getKey() + ") return value", expected, actual);
        }
    }

    @Test
    public void get_notOk() {
        Optional<Integer> expected = getFruitDaoImpl.get(notExistFruit);
        assertFalse("get(" + notExistFruit + ") should return Optional.Empty" , expected.isPresent());
    }

    @Test
    public void put_Ok() {
        int expectedSize = 0;
        for(Map.Entry<Fruit, Integer> entry : getFruitMap.entrySet()) {
            putFruitDaoImpl.put(entry.getKey(), entry.getValue());
            expectedSize++;
            assertEquals("Fruit storage size", expectedSize, putFruitMap.size());
            assertTrue("Fruit storage should contain fruit '" + entry.getKey() + "'",
                    putFruitMap.containsKey(entry.getKey()));
            assertEquals("Fruit storage amount for '" + entry.getKey() + "'",
                    entry.getValue(), putFruitMap.get(entry.getKey()));
        }
    }

    @Test
    public void put_Rewrite_Ok() {
        putFruitMap.putAll(getFruitMap);
        int expectedSize = putFruitMap.size();
        for(Map.Entry<Fruit, Integer> entry : getFruitMap.entrySet()) {
            Integer expectedValue = entry.getValue() % 13;
            putFruitDaoImpl.put(entry.getKey(), expectedValue);
            assertEquals("Fruit storage size", expectedSize, putFruitMap.size());
            assertTrue("Fruit storage should contain fruit '" + entry.getKey() + "'",
                    putFruitMap.containsKey(entry.getKey()));
            assertEquals("Fruit storage amount for '" + entry.getKey() + "'",
                    expectedValue, putFruitMap.get(entry.getKey()));
        }
    }

    private Map<Fruit, Integer> getGetFruitMap() {
        Map<Fruit, Integer> expectedMap = new HashMap<>();
        expectedMap.put(new Fruit("apple"), 30);
        expectedMap.put(new Fruit("banana"), 1000);
        expectedMap.put(new Fruit("fruit max"), Integer.MAX_VALUE);
        expectedMap.put(new Fruit("fruit min"), Integer.MIN_VALUE);
        return expectedMap;
    }
}