package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitInStorage;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static final String FIRST_LINE = "fruit,quantity";
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void generateReport_emptyList_Ok() {
        List<FruitInStorage> fruit = new ArrayList<>();
        assertEquals(FIRST_LINE, reportService.generateReport(fruit));
    }

    @Test
    void generateReport_validList_Ok() {
        List<FruitInStorage> fruit = List.of(new FruitInStorage("banana", 123),
                new FruitInStorage("apple", 0));
        String expected = FIRST_LINE + System.lineSeparator()
                + "banana,123" + System.lineSeparator()
                + "apple,0";
        assertEquals(expected, reportService.generateReport(fruit));
    }
}
