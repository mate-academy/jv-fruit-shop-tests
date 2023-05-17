package core.basesyntax.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Product;
import org.junit.After;
import org.junit.Test;

public class ReportServiceImplTest {
    private final ReportService reportService = new ReportServiceImpl();

    @After
    public void afterEachTest() {
        Storage.products.clear();
    }

    @Test
    public void createReport_Ok() {
        Storage.products.add(new Product("banana", 100));
        Storage.products.add(new Product("apple", 50));
        String expected0 = "name,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,50";
        String actual = reportService.createReport(Storage.products);
        assertEquals(expected0, actual);

        String expect1 = "name,quantity" + System.lineSeparator()
                + "banana,250" + System.lineSeparator()
                + "apple,475";
        assertNotEquals(expect1, actual);
    }

    @Test
    public void createReport_null_Ok() {
        String actual = reportService.createReport(Storage.products);
        assertNull(actual);
    }
}
