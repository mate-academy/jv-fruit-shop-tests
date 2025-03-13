package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private ReportCreator reportCreator;
    private Map<String, Integer> map;
    private List<String> expected;

    @BeforeEach
    void setUp() {
        Storage.fruitsStorage.put("apple", 10);
        Storage.fruitsStorage.put("banana", 20);
        map = Storage.fruitsStorage;
        reportCreator = new ReportGeneratorImpl();
    }

    @Test
    void createReport_Generating_IsOk() {
        expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,20");
        expected.add("apple,10");
        List<String> actual = reportCreator.createReport(map);
        assertEquals(expected, actual);
    }

    @AfterEach
    void clear_Storage() {
        Storage.fruitsStorage.clear();
    }
}
