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
    public void reportService_Ok() {
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
    public void reportService_emptyStorage_Ok() {
        String expected = "fruit,quantity";
        ReportService reportService = new ReportServiceImpl();
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void reportService_emptyFruitTransaction_NotOk() {
        Storage.fruits.put(null, null);
        ReportService reportService = new ReportServiceImpl();
        assertThrows(NullPointerException.class, reportService::createReport);
    }
}
