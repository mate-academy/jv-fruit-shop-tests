package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportMakerService;
import core.basesyntax.service.impl.ReportMakerServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportMakerServiceTest {
    private static final String EXCEPTED_VALID = "fruit,quantity" + System.lineSeparator()
            + "papaya,100" + System.lineSeparator()
            + "durian,72";
    private static final String EXCEPTED_EMPTY = "fruit,quantity";

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
        ReportMakerService reportMakerService = new ReportMakerServiceImpl();
        String actual = reportMakerService.createReport();
        Assert.assertEquals(EXCEPTED_VALID, actual);
    }

    @Test
    public void createReport_emptyInputData() {
        ReportMakerService reportMakerService = new ReportMakerServiceImpl();
        Storage.fruitStorage.clear();
        String actual = reportMakerService.createReport();
        Assert.assertEquals(EXCEPTED_EMPTY, actual);
    }
}
