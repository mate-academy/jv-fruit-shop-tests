package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.After;
import org.junit.Test;

public class ReportServiceTest {
    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    @Test
    public void createReport_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(80);
        Storage.fruits.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        String expected = "fruit,quantity" + System.lineSeparator() + "apple," + 80;
        ReportService reportService = new ReportServiceImpl();
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyStorage_Ok() {
        String expected = "fruit,quantity";
        ReportService reportService = new ReportServiceImpl();
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyFruitTransaction_NotOk() {
        Storage.fruits.put(null, null);
        ReportService reportService = new ReportServiceImpl();
        assertThrows(RuntimeException.class, reportService::createReport);
    }

    @Test
    public void createReport_nullFruit_NotOk() {
        Storage.fruits.put(null, 20);
        ReportService reportService = new ReportServiceImpl();
        assertThrows(RuntimeException.class, reportService::createReport);
    }

    @Test
    public void createReport_nullQuantity_NotOk() {
        Storage.fruits.put("apple", null);
        ReportService reportService = new ReportServiceImpl();
        assertThrows(RuntimeException.class, reportService::createReport);
    }
}
