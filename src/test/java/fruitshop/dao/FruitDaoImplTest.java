package fruitshop.dao;

import static org.junit.Assert.assertEquals;

import fruitshop.db.Storage;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FruitDaoImplTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private FruitDao fruitDao = new FruitDaoImpl();

    @Test
    public void addToStorage_uniqueKeys_ok() {
        fruitDao.addToStorage("apple", 20);
        fruitDao.addToStorage("banana", 35);
        assertEquals(2, Storage.fruitList.size());
        assertEquals(Integer.valueOf(20), Storage.fruitList.get("apple"));
        assertEquals(Integer.valueOf(35), Storage.fruitList.get("banana"));
    }

    @Test
    public void addToStorage_notUniqueKeys_ok() {
        fruitDao.addToStorage("apple", 20);
        fruitDao.addToStorage("apple", 10);
        fruitDao.addToStorage("banana", 35);
        fruitDao.addToStorage("apple", 55);
        assertEquals(2, Storage.fruitList.size());
        assertEquals(Integer.valueOf(55), Storage.fruitList.get("apple"));
        assertEquals(Integer.valueOf(35), Storage.fruitList.get("banana"));
    }

    @Test
    public void addValue_existingKey_ok() {
        Storage.fruitList.put("apple", 20);
        Storage.fruitList.put("banana", 10);
        fruitDao.addValue("apple", 10);
        fruitDao.addValue("banana", 35);
        assertEquals(2, Storage.fruitList.size());
        assertEquals(Integer.valueOf(30), Storage.fruitList.get("apple"));
        assertEquals(Integer.valueOf(45), Storage.fruitList.get("banana"));
    }

    @Test
    public void addValue_notExistingKey_notOk() {
        Storage.fruitList.put("apple", 20);
        Storage.fruitList.put("banana", 10);
        fruitDao.addValue("apple", 10);
        fruitDao.addValue("banana", 35);
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("Key doesn't exist");
        fruitDao.addValue("orange", 35);
    }

    @Test
    public void subtractValue_existingKey_ok() {
        Storage.fruitList.put("apple", 20);
        Storage.fruitList.put("banana", 10);
        fruitDao.subtractValue("apple", 10);
        fruitDao.subtractValue("banana", 5);
        assertEquals(2, Storage.fruitList.size());
        assertEquals(Integer.valueOf(10), Storage.fruitList.get("apple"));
        assertEquals(Integer.valueOf(5), Storage.fruitList.get("banana"));
    }

    @Test
    public void subtractValue_notExistingKey_notOk() {
        Storage.fruitList.put("apple", 20);
        Storage.fruitList.put("banana", 10);
        fruitDao.subtractValue("apple", 10);
        fruitDao.subtractValue("banana", 5);
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("Key doesn't exist");
        fruitDao.subtractValue("orange", 35);
    }

    @Test
    public void subtractValue_valueLessThanAvailableQuantity_notOk() {
        Storage.fruitList.put("apple", 20);
        Storage.fruitList.put("banana", 10);
        fruitDao.subtractValue("apple", 10);
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("We don't have enough fruits");
        fruitDao.subtractValue("banana", 15);
    }

    @After
    public void tearDown() {
        Storage.fruitList.clear();
    }
}
