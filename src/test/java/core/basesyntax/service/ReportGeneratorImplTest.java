package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportGeneratorImplTest {
    @Test
    public void makeReport_getHeader_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = new ReportGeneratorImpl().makeReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void makeReport_generateActualReport_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,200" + System.lineSeparator();
        Storage.storage.put("apple", 200);
        String actual = new ReportGeneratorImpl().makeReport();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
