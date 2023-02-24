package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.WarehouseDao;
import core.basesyntax.dao.WarehouseDaoImpl;
import core.basesyntax.db.Warehouse;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportCreator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportCreatorImplTest {
    private WarehouseDao warehouseDao;
    private ReportCreator reportCreator;

    @Before
    public void setUp() {
        warehouseDao = new WarehouseDaoImpl();
        reportCreator = new ReportCreatorImpl(warehouseDao);
    }

    @After
    public void afterEachTest() {
        Warehouse.warehouse.clear();
    }

    @Test
    public void getReport_ok() {
        FruitTransaction balanceBanana152 = new FruitTransaction();
        balanceBanana152.setFruit("banana");
        balanceBanana152.setQuantity(152);
        FruitTransaction balanceApple90 = new FruitTransaction();
        balanceApple90.setFruit("apple");
        balanceApple90.setQuantity(90);
        warehouseDao.setQuantity(balanceBanana152.getFruit(), balanceBanana152.getQuantity());
        warehouseDao.setQuantity(balanceApple90.getFruit(), balanceApple90.getQuantity());
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        String actual = reportCreator.getReport(warehouseDao.getLeftovers());
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_null_notOk() {
        assertThrows(RuntimeException.class, () -> {
            reportCreator.getReport(null);
        });
    }
}
