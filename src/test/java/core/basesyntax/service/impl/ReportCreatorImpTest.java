package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.InMemoryStorage;
import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImpTest {
    private final ReportService reportCreator = new ReportServiceImpl();

    @AfterAll
    public static void clearStorageAfterTest() {
        InMemoryStorage.items.clear();
    }

    @BeforeEach
    public void clearStorage() {
        InMemoryStorage.items.clear();
    }

    @Test
    void report_correctData_ok() {
        InMemoryStorage.items.put("lala", 1);
        InMemoryStorage.items.put("tata", -1);
        List<String> actual = reportCreator.report();
        List<String> expected = new ArrayList<>();
        expected.add("lala,1");
        expected.add("tata,-1");
        assertEquals(expected,actual);
    }

    @Test
    void report_empty_ok() {
        List<String> report = reportCreator.report();
        List<String> emptyList = new ArrayList<>();
        assertEquals(emptyList, report);
    }
}
