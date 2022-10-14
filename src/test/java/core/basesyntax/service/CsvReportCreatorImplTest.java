package core.basesyntax.service;

import core.basesyntax.db.Storage;
import org.junit.Assert;
import org.junit.Test;

public class CsvReportCreatorImplTest {
    private ReportCreator reportCreator = new CsvReportCreatorImpl();

    @Test
    public void csvReportCreatorImpl_correctData_Ok() {
        Storage.getStorage().put("ananas",150);
        String string = reportCreator.createReport(Storage.getStorage());
        Assert.assertEquals("ananas,150\n",string);
    }
}
