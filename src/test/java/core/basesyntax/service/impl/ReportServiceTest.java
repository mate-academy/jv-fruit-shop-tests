package core.basesyntax.service.impl;

import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceTest {
    private static final ReportService reportService = new ReportServiceImpl();

    @Before
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void reportService_correctData_ok() {
        Storage.fruits.put(new Fruit("banana"), 152);
        Storage.fruits.put(new Fruit("apple"), 90);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana" + "," + 152 + System.lineSeparator()
                + "apple" + "," + 90;
        String actual = reportService.getReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reportService_emptyStorage_notOk() {
        try {
            reportService.getReport();
        } catch (RuntimeException e) {
            return;
        }
        fail("Storage was empty");
    }
}

