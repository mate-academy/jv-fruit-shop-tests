package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.CreateReport;
import org.junit.After;
import org.junit.Test;

public class CreateReportImplTest {
    private final CreateReport createReport = new CreateReportImpl();

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void createReport_Ok() {
        Storage.fruits.put("banana", 59);
        Storage.fruits.put("apple", 14);
        String actual = createReport.createReport();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,59").append(System.lineSeparator())
                .append("apple,14");
        String expected = stringBuilder.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_EmptyStorage_Ok() {
        String actual = createReport.createReport();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity");
        String expected = stringBuilder.toString();
        assertEquals(expected, actual);
    }
}
