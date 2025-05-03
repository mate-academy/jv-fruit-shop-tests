package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();
    private static List<String> CORRECT_REPORT_WITH_DATA;
    private static List<String> REPORT_WITHOUT_DATA;

    @BeforeAll
    static void setUp() {
        CORRECT_REPORT_WITH_DATA = List.of("fruit,quantity",
                "apple,0",
                "banana,10",
                "orange,20");
        REPORT_WITHOUT_DATA = List.of("fruit,quantity");
    }

    @BeforeEach
    void clearStorage() {
        Storage.fruitStorage.clear();
    }

    @Test
    void createReport_ReportWithData_Ok() {
        Storage.fruitStorage.put("apple", 0);
        Storage.fruitStorage.put("banana", 10);
        Storage.fruitStorage.put("orange", 20);

        List<String> actualReport = reportGenerator.createReport();
        assertEquals(CORRECT_REPORT_WITH_DATA.size(), actualReport.size());
        assertEquals(CORRECT_REPORT_WITH_DATA.get(0), actualReport.get(0));
    }

    @Test
    void createReport_ReportWithEmptyData_Ok() {
        List<String> actualReport = reportGenerator.createReport();
        assertEquals(REPORT_WITHOUT_DATA, actualReport);
    }
}
