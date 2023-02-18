package core.basesyntax.service.impl.operation;

import core.basesyntax.dao.WarehouseDao;
import core.basesyntax.dao.WarehouseDaoImpl;
import core.basesyntax.db.Warehouse;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class SupplyOperationHandlerTest {
    private WarehouseDao warehouseDao;
    private FruitTransaction transaction;
    private SupplyOperationHandler supplyOperationHandler;

    @Before
    public void setUp() {
        Warehouse.warehouse.put("banana", 25);
        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit("banana");
        warehouseDao = new WarehouseDaoImpl();
        supplyOperationHandler = new SupplyOperationHandler(warehouseDao);
    }

    @After
    public void afterEachTest() {
        Warehouse.warehouse.clear();
    }

    @Test
    public void supply_ok() {
        transaction.setQuantity(25);
        int expected = Warehouse.warehouse.get("banana") + transaction.getQuantity();
        supplyOperationHandler.handle(transaction);
        int actual = Warehouse.warehouse.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void supplyNegativeQuantity_notOk() {
        transaction.setQuantity(-50);
        assertThrows(RuntimeException.class, () -> {
            supplyOperationHandler.handle(transaction);
        });
    }

    @Test
    public void supplyNullTransaction_notOk() {
        assertThrows(RuntimeException.class, () -> {
            supplyOperationHandler.handle(null);
        });
    }

    @Test
    public void supplyNewFruit_ok() {
        transaction.setFruit("apple");
        transaction.setQuantity(25);
        int expected = transaction.getQuantity();
        supplyOperationHandler.handle(transaction);
        int actual = Warehouse.warehouse.get("apple");
        assertEquals(expected, actual);
    }
}
