package core.basesyntax.service.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Storage;
import core.basesyntax.service.ReportService;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final ReportService REPORT_SERVICE = new ReportServiceImpl();

    @Before
    public void cleanMapDB() {
        Storage.getFruits().clear();
    }

    @Test
    public void testReportService_withFewLines_isOk() {
        Storage.getFruits().put("apple", 50);
        Storage.getFruits().put("banana", 12);
        String newQuantity = REPORT_SERVICE.createReport(Storage.getFruits());
        assertEquals("fruit,quantity\nbanana,12\napple,50", newQuantity);
    }
}
