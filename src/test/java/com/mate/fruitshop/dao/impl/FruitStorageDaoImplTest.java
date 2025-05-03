package com.mate.fruitshop.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.mate.fruitshop.dao.FruitStorageDao;
import com.mate.fruitshop.db.Storage;
import com.mate.fruitshop.model.FruitEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitStorageDaoImplTest {
    private static FruitStorageDao dao = new FruitStorageDaoImpl();

    @Before
    public void setUp() {
        Storage.fruits.add(new FruitEntry("banana", 10));
        Storage.fruits.add(new FruitEntry("apple", 1));
        Storage.fruits.add(new FruitEntry("orange", 3));
        Storage.fruits.add(new FruitEntry("kiwi", 100));
        Storage.fruits.add(new FruitEntry("grapes", 55));
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void getByName_PresentEntry_Ok() {
        FruitEntry testFruit = Storage.fruits.get(Storage.fruits.size() / 2);
        assertEquals(testFruit, dao.getByName(testFruit.getFruitName()));
    }

    @Test
    public void getByName_AbsentEntry_Ok() {
        FruitEntry testFruit = Storage.fruits.remove(Storage.fruits.size() / 2);
        assertNull(dao.getByName(testFruit.getFruitName()));
    }

    @Test
    public void add_NewEntry_Ok() {
        FruitEntry testFruit = Storage.fruits.remove(Storage.fruits.size() / 2);
        dao.add(testFruit);
        assertTrue(Storage.fruits.contains(testFruit));
    }

    @Test
    public void getAll_Ok() {
        assertEquals(Storage.fruits, dao.getAll());
    }
}
