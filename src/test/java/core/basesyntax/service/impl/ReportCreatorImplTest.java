package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.fruitsMap;
import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportCreator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static ReportCreator reportService;
    private static final String TITLE_FILE = "fruit,quantity\n";
    private static final String FIRST_LINE_FILE = "apple,15";
    private static final String SECOND_LINE_FILE = "orange,10\n";
    private static final String FIRST_WORD_FILE = "apple";
    private static final String SECOND_WORD_FILE = "orange";
    private static final int QUANTITY_FOR_APPLE = 15;
    private static final int QUANTITY_FOR_ORANGE = 10;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportCreatorImpl();
        fruitsMap.put(FIRST_WORD_FILE, QUANTITY_FOR_APPLE);
        fruitsMap.put(SECOND_WORD_FILE, QUANTITY_FOR_ORANGE);
    }

    @Test
    public void createReport_Ok() {
        String actual = reportService.doReport();
        assertEquals(TITLE_FILE
                + SECOND_LINE_FILE
                + FIRST_LINE_FILE, actual);
    }

    @AfterClass
    public static void afterClass() {
        fruitsMap.clear();
    }
}
