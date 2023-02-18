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

public class BalanceOperationHandlerTest {
    private WarehouseDao warehouseDao;
    private FruitTransaction transaction;
    private BalanceOperationHandler balanceOperationHandler;

    @Before
    public void setUp() {
        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("banana");
        warehouseDao = new WarehouseDaoImpl();
        balanceOperationHandler = new BalanceOperationHandler(warehouseDao);
    }

    @After
    public void afterEachTest() {
        Warehouse.warehouse.clear();
    }

    @Test
    public void setBalance_ok() {
        transaction.setQuantity(20);
        balanceOperationHandler.handle(transaction);
        int expected = transaction.getQuantity();
        int actual = Warehouse.warehouse.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void setBalance_negativeQuantity_notOk() {
        transaction.setQuantity(-15);
        assertThrows(RuntimeException.class, () -> {
            balanceOperationHandler.handle(transaction);
        });

    }
}