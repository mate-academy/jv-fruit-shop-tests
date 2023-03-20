package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.repository.FruitDB;
import core.basesyntax.service.ReportBuilder;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportBuilderImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String FRUIT_2 = "orange";
    private static final String FRUIT_WITH_AMOUNT = "orange,20";
    private static final int AMOUNT = 20;
    private static ReportBuilder reportBuilder;

    @BeforeClass
    public static void beforeClass() {
        reportBuilder = new ReportBuilderImpl();
        FruitDB.fruitsOnStock.clear();
    }

    @After
    public void tearDown() {
        FruitDB.fruitsOnStock.clear();
    }

    @Test
    public void buildReport_matchFruitQuantityInDB_Ok() {
        FruitDB.fruitsOnStock.put(FRUIT_2, AMOUNT);
        assertTrue(reportBuilder.buildReport().contains(FRUIT_WITH_AMOUNT));
    }

    @Test
    public void buildReport_containsHeaderByDefault_Ok() {
        assertEquals(HEADER, reportBuilder.buildReport());
    }

    @Test
    public void buildReport_hasIndents_Ok() {
        FruitDB.fruitsOnStock.put(FRUIT_2, AMOUNT);
        assertTrue(reportBuilder.buildReport().contains(System.lineSeparator()));
    }
}
