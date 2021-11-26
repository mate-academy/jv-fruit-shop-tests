package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    public static final String BANANA = "banana";
    public static final String APPLE = "apple";
    public static final Integer NUMBER_OF_BANANAS = 152;
    public static final Integer NUMBER_OF_APPLES = 90;
    public static final String CORRECT_REPORT = "fruit,quantity"
            + System.lineSeparator() + "banana,152"
            + System.lineSeparator() + "apple,90";
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void correctReport_Ok() {
        Storage.storage.clear();
        Storage.storage.put(new Fruit(BANANA), NUMBER_OF_BANANAS);
        Storage.storage.put(new Fruit(APPLE), NUMBER_OF_APPLES);
        assertEquals(CORRECT_REPORT, reportService.formReport());
    }
}
