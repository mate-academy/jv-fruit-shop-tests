package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static final String FIRST_LINE = "fruit,quantity";
    private static final String CSV_COLUMNS_SPLITTER = ",";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static ReportService reportService;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void getReport_correctData_ok() {
        StringBuilder builder = new StringBuilder();
        builder.append(FIRST_LINE).append(LINE_SEPARATOR);
        for (Map.Entry<Fruit, Integer> row : Storage.storage.entrySet()) {
            builder.append(row.getKey().getName());
            builder.append(CSV_COLUMNS_SPLITTER);
            builder.append(row.getValue());
            builder.append(LINE_SEPARATOR);
        }
        Assert.assertEquals(builder.toString(), reportService.getReport());
    }

    @Test
    public void getReport_withoutFirstLine_notOk() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Fruit, Integer> row : Storage.storage.entrySet()) {
            builder.append(row.getKey().getName());
            builder.append(CSV_COLUMNS_SPLITTER);
            builder.append(row.getValue());
            builder.append(LINE_SEPARATOR);
        }
        Assert.assertNotEquals(builder.toString(), reportService.getReport());
    }

    @Test
    public void getReport_withoutLinesSeparator_notOk() {
        StringBuilder builder = new StringBuilder();
        builder.append(FIRST_LINE);
        for (Map.Entry<Fruit, Integer> row : Storage.storage.entrySet()) {
            builder.append(row.getKey().getName());
            builder.append(CSV_COLUMNS_SPLITTER);
            builder.append(row.getValue());
        }
        Assert.assertNotEquals(builder.toString(), reportService.getReport());
    }
}
