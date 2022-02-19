package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.Storage;
import core.basesyntax.dataservice.ReportCreator;
import core.basesyntax.dataservice.ReportCreatorImpl;
import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

public class ReportCreatorImplTest {
    private final ReportCreator reportCreator = new ReportCreatorImpl();
    private List<String> correctList = new ArrayList<>(List.of("fruit,quantity",
            "banana,152",
            "apple,90"));

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }

    @Test
    public void createReport_storageIsEmpty_notOk() {
        assertThrows(RuntimeException.class,
                reportCreator::createReport);
    }

    @Test
    public void storage_isNull_notOk() {
        Storage.storage.put(null, null);
        assertThrows(NullPointerException.class,
                reportCreator::createReport);
    }

    @Test
    public void createReport_correctReturnData_ok() {
        List<String> actual = correctList;
        Storage.storage.put(new Fruit("banana"), 152);
        Storage.storage.put(new Fruit("apple"), 90);
        List<String> expected = reportCreator.createReport();
        assertEquals(expected, actual);
    }
}
