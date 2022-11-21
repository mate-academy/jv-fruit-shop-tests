package core.basesyntax.service;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ServiceReportImplTest {
    private static final String expectedString = "Malala,1234567890";
    private static final ServiceReportImpl serviceReport = new ServiceReportImpl();

    @Test
    public void checkMakingReport_OK() {
        storage.put("Malala", 1234567890);
        assertEquals(expectedString, serviceReport.makeReport());
        storage.clear();
    }
}
