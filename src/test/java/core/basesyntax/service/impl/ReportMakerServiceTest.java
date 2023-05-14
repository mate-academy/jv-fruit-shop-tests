package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportMakerService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerServiceTest {
    private static final String EXCEPTED_VALID = "fruit,quantity" + System.lineSeparator()
            + "papaya,100" + System.lineSeparator()
            + "durian,72";
    private static final String EXCEPTED_EMPTY = "fruit,quantity";
    private static ReportMakerService reportMakerService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportMakerService = new ReportMakerServiceImpl();
    }

    @Before
    public void setUpStorage() {
        Storage.fruitStorage.put("papaya", 100);
        Storage.fruitStorage.put("durian", 72);
    }

    @After
    public void clearStorage() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void createReport_validInputData() {
        String actual = reportMakerService.createReport();
        Assert.assertEquals(EXCEPTED_VALID, actual);
    }

    @Test
    public void createReport_emptyInputData() {
        Storage.fruitStorage.clear();
        String actual = reportMakerService.createReport();
        Assert.assertEquals(EXCEPTED_EMPTY, actual);
    }
}
