package core.basesyntax.report;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private ReportService reportService;
    private FruitStorage fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorage();
        reportService = new ReportService(fruitStorage);
    }

    @Test
    void generate_emptyInventoryReport_ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportService.generateReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void generate_singleFruitReport_ok() {
        fruitStorage.addFruit("apple", 5);

        String expectedReport = "fruit,quantity\napple,5\n";
        String actualReport = reportService.generateReport();

        assertEquals(
                List.of(expectedReport.lines().toArray()),
                List.of(actualReport.lines().toArray())
        );
    }
}
