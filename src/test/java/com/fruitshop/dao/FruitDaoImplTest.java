package com.fruitshop.dao;

import com.fruitshop.db.DataBase;
import com.fruitshop.model.Fruit;
import com.fruitshop.strategy.ElementDoesNotExist;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private final String lemon = "Lemon";
    private final String kivi = "kivi";
    private FruitDao fruitDao;
    private Fruit fruitOne;
    private Fruit fruitTwo;

    @BeforeEach
    void setUp() {
        fruitDao = new FruitDaoImpl();
        fruitOne = new Fruit();
        fruitOne.setBalance(50);
        fruitTwo = new Fruit();
        fruitTwo.setPurchase(100);
    }

    @AfterEach
    void tearDown() {
        DataBase.fruitsInShop.clear();
    }

    @Test
    void add_Ok() {
        fruitDao.add(lemon, fruitOne);
        int expected = 1;
        Assertions.assertEquals(expected, DataBase.fruitsInShop.size());
        Assertions.assertEquals(fruitOne, DataBase.fruitsInShop.get(lemon));
    }

    @Test
    void add_KeyIsNull_NotOk() {
        Assertions.assertThrows(ElementDoesNotExist.class, () -> fruitDao.add(null, fruitOne));
    }

    @Test
    void add_ValueIsNull_NotOk() {
        Assertions.assertThrows(ElementDoesNotExist.class, () -> fruitDao.add("lemon", null));
    }

    @Test
    void get_ValueIsnull_notOk() {
        Assertions.assertThrows(ElementDoesNotExist.class, () -> fruitDao.get(null));
    }

    @Test
    void get_Ok() {
        DataBase.fruitsInShop.put(lemon, fruitOne);
        DataBase.fruitsInShop.put(kivi, fruitTwo);
        Assertions.assertEquals(fruitOne, fruitDao.get(lemon));
        Assertions.assertEquals(fruitTwo, fruitDao.get(kivi));
    }

    @Test
    void getAll_Ok() {
        Map<String, Fruit> expectedMap = new TreeMap<>();
        expectedMap.put(lemon, fruitOne);
        expectedMap.put(kivi, fruitTwo);
        DataBase.fruitsInShop.put(lemon, fruitOne);
        DataBase.fruitsInShop.put(kivi, fruitTwo);
        Assertions.assertEquals(expectedMap, fruitDao.getAll());
    }
}
