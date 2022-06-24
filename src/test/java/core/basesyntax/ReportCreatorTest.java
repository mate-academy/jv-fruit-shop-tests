package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.StorageSupplyService;
import core.basesyntax.service.impl.ReportCreatorImpl;
import core.basesyntax.service.impl.StorageSupplyServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorTest {
    private static Storage localStorage = new Storage();
    private static StorageSupplyService storageSupplyService =
            new StorageSupplyServiceImpl(localStorage);
    private static ReportCreator localReportCreator;

    @BeforeClass
    public static void fill() {
        localReportCreator = new ReportCreatorImpl();
        storageSupplyService.add("banana", 50);
        storageSupplyService.add("apple", 50);
        storageSupplyService.add("ananas", 50);
    }

    @Test
    public void makeReportHead_Ok() {
        List<String[]> reportToCheck = localReportCreator.getReport(localStorage);
        String[] headGot = reportToCheck.get(0);
        String[] headExpected = new String[]{"fruit", "quantity"};
        for (int i = 0; i < 2; i++) {
            assertEquals(headGot[i], headExpected[i]);
        }
    }

    @Test
    public void makeReportBody_Ok() {
        localReportCreator.reportFlush();
        List<String[]> bodyActual = localReportCreator.getReport(localStorage);
        bodyActual.remove(0);
        List<String[]> bodyExpected = new ArrayList<>();
        bodyExpected.add(new String[]{"banana", "50"});
        bodyExpected.add(new String[]{"apple", "50"});
        bodyExpected.add(new String[]{"ananas", "50"});
        for (int j = 0; j < bodyActual.size(); j++) {
            String[] actualLine = bodyActual.get(j);
            String[] expectedLine = bodyExpected.get(j);
            for (int i = 0; i < actualLine.length; i++) {
                assertEquals(actualLine[i], expectedLine[i]);
            }
        }
    }
}
