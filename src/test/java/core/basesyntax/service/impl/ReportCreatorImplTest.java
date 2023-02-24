package core.basesyntax.service.impl;

import core.basesyntax.dao.WarehouseDao;
import core.basesyntax.dao.WarehouseDaoImpl;
import core.basesyntax.db.Warehouse;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportCreator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
        FruitTransaction bBanana152 = new FruitTransaction();
        bBanana152.setFruit("banana");
        bBanana152.setQuantity(152);
        FruitTransaction bApple90 = new FruitTransaction();
        bApple90.setFruit("apple");
        bApple90.setQuantity(90);
        warehouseDao.setQuantity(bBanana152.getFruit(), bBanana152.getQuantity());
        warehouseDao.setQuantity(bApple90.getFruit(), bApple90.getQuantity());
        String expected = "fruit,quantity" + System.lineSeparator() + "banana,152" + System.lineSeparator() + "apple,90";
        String actual = reportCreator.getReport(warehouseDao.getLeftovers());
        assertEquals(expected, actual);
    }

    @Test
    public void getReportNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            reportCreator.getReport(null);
        });
    }
}
