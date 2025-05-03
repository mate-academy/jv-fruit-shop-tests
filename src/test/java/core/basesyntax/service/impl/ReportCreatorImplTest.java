package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class ReportCreatorImplTest {
    private final ReportCreator reportCreator = new ReportCreatorImpl();

    @Test
    public void createReportList_valid_ok() {
        Storage.storage.putAll(Map.of("banana", 100, "apple", 90));
        List<String> expected = List.of("fruit,quantity" + System.lineSeparator(),
                "banana,100" + System.lineSeparator(), "apple,90" + System.lineSeparator());
        List<String> actual = reportCreator.createReportList();
        assertEquals(expected, actual);
    }

    @Test
    public void createReportList_emptyStorage_ok() {
        List<String> expected = List.of("fruit,quantity" + System.lineSeparator());
        List<String> actual = reportCreator.createReportList();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
