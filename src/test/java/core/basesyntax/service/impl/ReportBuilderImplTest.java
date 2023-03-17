package core.basesyntax.service.impl;

import core.basesyntax.repository.FruitDB;
import core.basesyntax.service.ReportBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportBuilderImplTest extends Assert {
    private static final String HEADER = "fruit,quantity";
    private static final String VALID_REPORT_PART = "fruit,quantity\nbanana,20";
    private static final String FRUIT_1 = "banana";
    private static final String FRUIT_2 = "orange";
    private static final String FRUIT_WITH_AMOUNT = "orange,20";
    private static final int AMOUNT = 20;
    private static ReportBuilder reportBuilder;

    @BeforeClass
    public static void beforeClass() {
        reportBuilder = new ReportBuilderImpl();
    }

    @Before
    public void setUp() {
        FruitDB.fruitsOnStock.put(FRUIT_1, AMOUNT);

    }

    @After
    public void tearDown() {
        FruitDB.fruitsOnStock.clear();
    }

    @Test
    public void buildReport_containsHeaderByDefault_Ok() {
        FruitDB.fruitsOnStock.clear();
        assertEquals(HEADER, reportBuilder.buildReport());
    }

    @Test
    public void buildReport_matchFruitQuantityInDB_Ok() {
        FruitDB.fruitsOnStock.put(FRUIT_2, AMOUNT);
        assertTrue(reportBuilder.buildReport().contains(FRUIT_WITH_AMOUNT));
    }

    @Test
    public void buildReport_hasIndents_Ok() {
        FruitDB.fruitsOnStock.put(FRUIT_2, AMOUNT);
        assertTrue(reportBuilder.buildReport().contains(System.lineSeparator()));
    }
}
