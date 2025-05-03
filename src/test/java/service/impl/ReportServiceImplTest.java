package service.impl;

import db.Storage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    @AfterAll
    static void clearStorage() {
        Storage.STORAGE.clear();
    }

    @Test
    void createNewReport_ok() {
        ReportServiceImpl reportService = new ReportServiceImpl();
        final String separator = System.lineSeparator();
        String expected = "fruit,quantity"
                + separator
                + "banana,20"
                + separator + "orange,5";

        Storage.STORAGE.put("banana", 20);
        Storage.STORAGE.put("orange", 5);

        Assertions.assertEquals(expected, reportService.createReport());
    }
}
