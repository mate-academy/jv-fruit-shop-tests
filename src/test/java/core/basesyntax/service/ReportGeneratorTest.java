package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    @Test
    void report_Generating_IsOk() {
        Storage.fruitsStorage.put("apple", 10);
        Storage.fruitsStorage.put("banana", 20);
        Map<String, Integer> map = Storage.fruitsStorage;
        ReportCreator reportCreator = new ReportGeneratorImpl();
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,20");
        expected.add("apple,10");
        List<String> actual = reportCreator.createReport(map);
        assertEquals(expected, actual);
        Storage.fruitsStorage.clear();
    }
}
