package core.basesyntax.services.imp;

import core.basesyntax.db.Storage;
import core.basesyntax.services.ReportCreaterService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportCreaterServiceImpTest {

    private final ReportCreaterService reportCreaterService = new ReportCreaterServiceImp();

    @Test
    public void create_correctReport_OK() {
        Storage.storage.put("banana",50);
        Storage.storage.put("apple",20);
        String expected = "fruit,quantity"
                + System.lineSeparator() + "banana,50"
                + System.lineSeparator() + "apple,20"
                + System.lineSeparator();
        String actual = reportCreaterService.createReport();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void create_NonCorrectReport_OK() {
        Storage.storage.put("banana",1000);
        Storage.storage.put("apple",1000);
        String expected = "fruit,quantity"
                + System.lineSeparator() + "banana,50"
                + System.lineSeparator() + "apple,20"
                + System.lineSeparator();
        String actual = reportCreaterService.createReport();
        Assert.assertNotEquals(expected,actual);
    }

    @Test
    public void create_NullKeyAndValue_OK() {
        Storage.storage.put(null,null);
        Storage.storage.put(null,null);
        String expected = "fruit,quantity"
                + System.lineSeparator() + "null,null"
                + System.lineSeparator();
        String actual = reportCreaterService.createReport();
        Assert.assertEquals(expected,actual);

    }

    @After
    public void clear() {
        Storage.storage.clear();
    }
}
