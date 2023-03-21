package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.repository.FruitDB;
import core.basesyntax.service.ReportBuilder;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportBuilderImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String FRUIT_2 = "orange";
    private static final String RESULT_LINE = "fruit,quantity" + System.lineSeparator()
            + "orange,20";
    private static final int AMOUNT = 20;
    private static ReportBuilder reportBuilder;

    @BeforeClass
    public static void beforeClass() {
        reportBuilder = new ReportBuilderImpl();
    }

    @After
    public void tearDown() {
        FruitDB.fruitsOnStock.clear();
    }

    @Test
    public void buildReport_matchFruitQuantityInDB_Ok() {
        FruitDB.fruitsOnStock.put(FRUIT_2, AMOUNT);
        assertEquals(RESULT_LINE, reportBuilder.buildReport());
    }

    @Test
    public void buildReport_containsHeaderByDefault_Ok() {
        assertEquals(HEADER, reportBuilder.buildReport());
    }
}
