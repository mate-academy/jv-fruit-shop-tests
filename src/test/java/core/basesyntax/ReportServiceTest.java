package core.basesyntax;

import static org.hamcrest.CoreMatchers.startsWith;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReportServiceTest {

    private static final String expectedReport = "fruit,quantity"
            + System.lineSeparator()
            + "apple,25"
            + System.lineSeparator()
            + "banana,90";
    private static final ReportService reportService = new ReportServiceImpl();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testReportService() {
        Fruit banana = new Fruit("banana");
        Fruit apple = new Fruit("apple");

        Storage.getStore().clear();
        Storage.getStore().put(apple, 25);
        Storage.getStore().put(banana, 90);

        String report = reportService.getReportText();
        Assert.assertEquals(expectedReport, report);
    }

    @Test
    public void testWriteReport() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(startsWith("Can't write report file"));
        reportService.writeReport(null, null);
    }
}
