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

public class PurchaseOperationHandlerTest {
    private WarehouseDao warehouseDao;
    private FruitTransaction transaction;
    private PurchaseOperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        Warehouse.warehouse.put("banana", 25);
        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit("banana");
        warehouseDao = new WarehouseDaoImpl();
        purchaseOperationHandler = new PurchaseOperationHandler(warehouseDao);
    }

    @After
    public void afterEachTest() {
        Warehouse.warehouse.clear();
    }

    @Test
    public void handle_sale_ok() {
        transaction.setQuantity(20);
        int expected = Warehouse.warehouse.get("banana") - transaction.getQuantity();
        purchaseOperationHandler.handle(transaction);
        int actual = Warehouse.warehouse.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void handle_saleNegativeQuantity_notOk() {
        transaction.setQuantity(-50);
        assertThrows(RuntimeException.class, () -> {
            purchaseOperationHandler.handle(transaction);
        });
    }

    @Test
    public void handle_saleZeroQuantity_notOk() {
        transaction.setQuantity(0);
        assertThrows(RuntimeException.class, () -> {
            purchaseOperationHandler.handle(transaction);
        });
    }

    @Test
    public void handle_saleSuperiorQuantity_notOk() {
        transaction.setQuantity(50);
        assertThrows(RuntimeException.class, () -> {
            purchaseOperationHandler.handle(transaction);
        });
    }

    @Test
    public void handle_fruitNull_notOk() {
        transaction.setFruit(null);
        assertThrows(RuntimeException.class, () -> {
            purchaseOperationHandler.handle(transaction);
        });
    }

    @Test
    public void handle_transactionNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            purchaseOperationHandler.handle(null);
        });
    }
}
