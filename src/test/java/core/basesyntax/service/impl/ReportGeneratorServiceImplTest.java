package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneratorService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorServiceImplTest {
    private static final List<String> EXPECTED_RESULT = List.of(
            "fruit,quantity", "banana,90", "apple,100");
    private static ReportGeneratorService reportGeneratorService;

    @BeforeAll
    static void beforeAll() {
        reportGeneratorService = new ReportGeneratorServiceImpl();
        Storage.STORAGE.clear();
        Storage.STORAGE.put("banana", 90);
        Storage.STORAGE.put("apple", 100);
    }

    @Test
    void createReport_correctReport_Ok() {
        assertEquals(EXPECTED_RESULT, reportGeneratorService.generateReport());
    }
}
