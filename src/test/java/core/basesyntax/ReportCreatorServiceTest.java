package core.basesyntax;

import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.DataProcessingService;
import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportCreatorServiceTest {
    private static final String SEPARATOR = ",";
    private static DataProcessingService dataProcessingService;
    private static final ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl();

    @Test
    public void createReport_emptyStorage() {
        try {
            reportCreatorService.createReport();
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown is storage is empty");
    }

    @Test
    public void createReport_correctData_ok() {
        Storage.fruitBalance.put(new Fruit("cherry"), 100);
        Storage.fruitBalance.put(new Fruit("apple"), 100);
        Storage.fruitBalance.put(new Fruit("banana"), 100);

        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "cherry" + SEPARATOR + 100 + System.lineSeparator()
                + "banana" + SEPARATOR + 100 + System.lineSeparator()
                + "apple" + SEPARATOR + 100;
        String actualReport = reportCreatorService.createReport();
        Assert.assertEquals(expectedReport, actualReport);
    }

    @After
    public void afterEachTest() {
        Storage.fruitBalance.clear();
    }
}
