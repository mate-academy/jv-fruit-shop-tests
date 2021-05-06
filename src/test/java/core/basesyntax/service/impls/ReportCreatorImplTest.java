package core.basesyntax.service.impls;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.After;
import org.junit.Test;

public class ReportCreatorImplTest {
    private String excepted;
    private final ReportCreator report = new ReportCreatorImpl();

    @Test
    public void reportCreating_isOk() {
        Storage.shopDatabase.put("banana", 10);
        excepted = "banana,10" + System.lineSeparator();
        assertEquals(excepted, report.getReport());
    }

    @Test
    public void reportFromEmptyDb_isOk() {
        excepted = "";
        assertEquals(excepted, report.getReport());
    }

    @After
    public void tearDown() {
        Storage.shopDatabase.clear();
    }
}
