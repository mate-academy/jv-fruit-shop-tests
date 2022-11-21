package core.basesyntax.service;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ServiceReportImplTest {
    private static final String expectedString = "fruit,quantity\ncherry,890\n";
    private static final ServiceReportImpl serviceReport = new ServiceReportImpl();

    @Test
    public void checkMakingReport_OK() {
        storage.put("cherry", 890);
        assertEquals(expectedString, serviceReport.makeReport());
        storage.clear();
    }
}
