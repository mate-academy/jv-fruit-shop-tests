package core.basesyntax.dao;

import core.basesyntax.db.Warehouse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class WarehouseDaoImplTest {
    private WarehouseDao warehouseDao;
    private String fruit;
    private Integer quantity;

    @Before
    public void setUp() throws Exception {
        warehouseDao = new WarehouseDaoImpl();
        fruit = "banana";
        quantity = 20;
    }

    @After
    public void afterEachTest() {
        Warehouse.warehouse.clear();
    }

    @Test
    public void setQuantity_ok() {
        warehouseDao.setQuantity(fruit, quantity);
        Integer expected = quantity;
        Integer actual = Warehouse.warehouse.get(fruit);
        assertEquals(expected, actual);
    }

    @Test
    public void setQuantity_nullQuantity_notOk() {
        assertThrows(RuntimeException.class, () -> {
            warehouseDao.setQuantity(fruit, null);
        });
    }

    @Test
    public void setQuantity_nullFruit_notOk() {
        assertThrows(RuntimeException.class, () -> {
            warehouseDao.setQuantity(null, quantity);
        });
    }

    @Test
    public void getQuantity_ok() {
        Warehouse.warehouse.put(fruit, quantity);
        Integer expected = Warehouse.warehouse.get(fruit);
        Integer actual = warehouseDao.getQuantity(fruit);
        assertEquals(expected, actual);
    }

    @Test
    public void getQuantity_notOk() {
        assertThrows(RuntimeException.class, () -> {
            warehouseDao.getQuantity(null);
        });
    }

    @Test
    public void isPresent_ok() {
        Warehouse.warehouse.put(fruit, quantity);
        assertTrue(warehouseDao.isPresent(fruit));
        assertFalse(warehouseDao.isPresent(null));
        assertFalse(warehouseDao.isPresent("apple"));
    }

    @Test
    public void getLeftovers() {
        Warehouse.warehouse.put(fruit, quantity);
        Warehouse.warehouse.put("apple", 30);
        Map<String, Integer> leftovers = warehouseDao.getLeftovers();
        assertEquals(Warehouse.warehouse, leftovers);

    }
}
