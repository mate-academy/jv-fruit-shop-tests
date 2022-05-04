package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportServiceTest {

    @Test
    public void report_validInputDataAlreadyInStorage_Ok() {
        StorageDao storageDao = new StorageDaoImpl();
        ReportService reportService = new ReportServiceImpl(storageDao);
        Fruit fruit = new Fruit("Banana");
        Storage.fruitStorage.put(fruit, 10);

        String actual = reportService.report();
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "Banana,10"
                + System.lineSeparator();

        Assert.assertEquals(actual, expected);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}
