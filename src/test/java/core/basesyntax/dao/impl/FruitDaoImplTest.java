package core.basesyntax.dao.impl;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Accountable;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private FruitDaoImpl dao;

    @BeforeEach
    void setUp() {
        dao = new FruitDaoImpl();
    }

    @AfterEach
    void reset() {
        Storage.getFruits().clear();
    }

    @Test
    void add_validFruit_ok() {
        Accountable fruit = new Fruit("apple");
        dao.add(fruit);

        assertEquals(1, Storage.getFruits().size());
        assertEquals("apple", Storage.getFruits().get(0).getName());
    }

    @Test
    void get_existingFruit_ok() {
        dao.add(new Fruit("banana"));
        Accountable result = dao.get("banana");

        assertNotNull(result);
        assertEquals("banana", result.getName());
    }

    @Test
    void get_notExistingFruit_returnsNull() {
        Accountable result = dao.get("orange");
        assertNull(result);
    }

    @Test
    void create_validFruit_ok() {
        Accountable fruit = dao.create("apple");

        assertNotNull(fruit);
        assertEquals("apple", fruit.getName());
        assertEquals(1, Storage.getFruits().size());
    }

    @Test
    void isExist_whenFruitExists_true() {
        dao.add(new Fruit("apple"));
        boolean result = dao.isExist("apple");

        assertTrue(result);
    }

    @Test
    void isExist_whenFruitNotExists_false() {
        boolean result = dao.isExist("banana");

        assertFalse(result);
    }

    @Test
    void getAll_returnsAllFruits() {
        dao.add(new Fruit("apple"));
        dao.add(new Fruit("banana"));

        List<Accountable> result = dao.getAll();

        assertEquals(2, result.size());
    }
}
