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
    private static Storage storage;
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void fillStorage() {
        storage = new Storage();
        StorageSupplyService storageSupplyService = new StorageSupplyServiceImpl(storage);
        reportCreator = new ReportCreatorImpl();
        storageSupplyService.add("banana", 50);
        storageSupplyService.add("apple", 50);
        storageSupplyService.add("ananas", 50);
    }

    @Test
    public void make_Report_Ok() {
        List<String[]> actualReport = reportCreator.getReport(storage);
        String[] actualHead = actualReport.get(0);
        String[] expectedHead = new String[]{"fruit", "quantity"};
        for (int i = 0; i < 2; i++) {
            assertEquals("head is wrong, check it", actualHead[i], expectedHead[i]);
        }
        actualReport.remove(0);
        List<String[]> bodyExpected = new ArrayList<>();
        bodyExpected.add(new String[]{"banana", "50"});
        bodyExpected.add(new String[]{"apple", "50"});
        bodyExpected.add(new String[]{"ananas", "50"});
        for (int j = 0; j < actualReport.size(); j++) {
            String[] actualLine = actualReport.get(j);
            String[] expectedLine = bodyExpected.get(j);
            for (int i = 0; i < actualLine.length; i++) {
                assertEquals("reports body is wrong, check it", actualLine[i], expectedLine[i]);
            }
        }
    }
}
