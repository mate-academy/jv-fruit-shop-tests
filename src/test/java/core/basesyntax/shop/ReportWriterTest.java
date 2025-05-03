package core.basesyntax.shop;

import core.basesyntax.shop.db.Storage;
import core.basesyntax.shop.model.Fruit;
import core.basesyntax.shop.service.ReportWriter;
import core.basesyntax.shop.service.ReportWriterImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriterTest {
    private static final String HEADER = "fruit,quantity";
    private static ReportWriter reportService;

    @BeforeClass
    public static void beforeClass() {
        Storage.fruitsCount.clear();
        reportService = new ReportWriterImpl();
    }

    @Test
    public void makeReport_emptyStorage_Ok() {
        Assert.assertEquals(HEADER,reportService.makeReport());
    }

    @Test
    public void makeReport_correctStorage_Ok() {
        Storage.fruitsCount.put(new Fruit("banana"), 30);
        Storage.fruitsCount.put(new Fruit("apple"), 20);
        String expected = HEADER + System.lineSeparator() + "banana,30"
                + System.lineSeparator() + "apple,20";
        String actual = reportService.makeReport();
        Assert.assertEquals(expected,actual);
    }

    @After
    public void tearDown() {
        Storage.fruitsCount.clear();
    }
}
