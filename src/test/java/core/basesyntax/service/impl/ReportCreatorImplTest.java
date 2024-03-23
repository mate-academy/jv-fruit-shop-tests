package core.basesyntax.service.impl;

import core.basesyntax.db.ProductStorage;
import core.basesyntax.service.ReportCreator;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportCreatorImplTest {
    public static final List<String> EXPECTED_REPORT = List.of(
            "product,quantity",
            "apple,10",
            "banana,20",
            "orange,5"
    );
    private static ReportCreator reportCreator;

    @BeforeAll
    static void beforeAll() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    public void create_NoData_ReturnsTitleOnly() {
        ProductStorage.STORAGE.clear();
        List<String> report = reportCreator.create();
        Assertions.assertEquals(1, report.size());
        Assertions.assertEquals(ReportCreatorImpl.TITLE, report.get(0));
    }

    @Test
    public void create_WithData_ReturnsReportLines() {
        ProductStorage.STORAGE.put("apple", 10);
        ProductStorage.STORAGE.put("banana", 20);
        ProductStorage.STORAGE.put("orange", 5);
        List<String> actualReport = reportCreator.create();
        Assertions.assertEquals(EXPECTED_REPORT, actualReport);
    }
}
