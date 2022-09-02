package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class ReportServiceImplTest {
    private ReportService reportService = new ReportServiceImpl();

    @Test
    public void report_written_Okey() {
        Storage.clear();
        Storage.put(new Fruit("banana"),152);
        Storage.put(new Fruit("apple"),90);
        Assert.assertEquals("fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator(), reportService.getReport());
    }
}
