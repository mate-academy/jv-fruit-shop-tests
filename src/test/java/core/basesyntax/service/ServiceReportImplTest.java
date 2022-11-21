package core.basesyntax.service;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FileWriterImpl;
import org.junit.Test;

public class ServiceReportImplTest {
    private static final String PATH_OUTPUT = "src/test/resources/output.csv";
    private static final String expectedString = "Malala,1234567890";
    private static final ServiceReportImpl serviceReport = new ServiceReportImpl();

    @Test
    public void checkMakingReport_OK() {
        storage.put("Malala", 1234567890);
        String string = serviceReport.makeReport();
        assertEquals(expectedString, serviceReport.makeReport());
        new FileWriterImpl().write(PATH_OUTPUT, string);
        storage.clear();
    }
}
