package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.service.CreatingReport;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreatingReportImplTest {
    private static final String EXPECTED_REPORT = "fruit,quantity\n"
                                                    + "banana,100\n"
                                                    + "apple,100";
    private static CreatingReport creatingReport;

    @BeforeClass
    public static void setCreatingReport() {
        creatingReport = new CreatingReportImpl();
    }

    @Before
    public void fillStorage() {
        FruitsStorage.fruitsStorage.put("banana", 100);
        FruitsStorage.fruitsStorage.put("apple", 100);
    }

    @Test
    public void createReport_ok() {
        String actualResult = creatingReport.createReport();
        assertEquals(EXPECTED_REPORT, actualResult);
    }

    @After
    public void clearStorage() {
        FruitsStorage.fruitsStorage.clear();
    }
}
