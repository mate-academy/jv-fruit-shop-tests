package core.basesyntax.service.impl.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.WarehouseDao;
import core.basesyntax.dao.WarehouseDaoImpl;
import core.basesyntax.db.Warehouse;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private WarehouseDao warehouseDao;
    private FruitTransaction transaction;
    private ReturnOperationHandler returnOperationHandler;

    @Before
    public void setUp() {
        Warehouse.warehouse.put("banana", 25);
        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("banana");
        warehouseDao = new WarehouseDaoImpl();
        returnOperationHandler = new ReturnOperationHandler(warehouseDao);
    }

    @After
    public void afterEachTest() {
        Warehouse.warehouse.clear();
    }

    @Test
    public void handle_return_ok() {
        transaction.setQuantity(25);
        int expected = Warehouse.warehouse.get("banana") + transaction.getQuantity();
        returnOperationHandler.handle(transaction);
        int actual = Warehouse.warehouse.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void handle_returnNegativeQuantity_notOk() {
        transaction.setQuantity(-50);
        assertThrows(RuntimeException.class, () -> {
            returnOperationHandler.handle(transaction);
        });
    }

    @Test
    public void handle_returnNullTransaction_notOk() {
        assertThrows(RuntimeException.class, () -> {
            returnOperationHandler.handle(null);
        });
    }

    @Test
    public void handle_returnNewFruit_ok() {
        transaction.setFruit("apple");
        transaction.setQuantity(25);
        int expected = transaction.getQuantity();
        returnOperationHandler.handle(transaction);
        int actual = Warehouse.warehouse.get("apple");
        assertEquals(expected, actual);
    }
}
