package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.BalanceStorageDao;
import core.basesyntax.db.impl.InMemoryBalanceStorageDao;
import core.basesyntax.model.Product;
import core.basesyntax.service.ReportGenerator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceReportGeneratorTest {
    private final BalanceStorageDao balanceStorage = InMemoryBalanceStorageDao.getInstance();
    private final ReportGenerator reportGenerator = new BalanceReportGenerator(balanceStorage);
    private final Product product = new Product("product");
    private final Product product2 = new Product("product2");

    @BeforeEach
    void setUp() {
        balanceStorage.clear();
    }

    @Test
    void getReport_Ok() {
        balanceStorage.save(product, 10);
        balanceStorage.save(product2, -22);
        List<String> expectedReport = List.of(
                "fruit,quantity",
                "product,10",
                "product2,-22"
        );
        List<String> report = reportGenerator.getReport();
        assertEquals(expectedReport, report);
    }

    @Test
    void getReport_empty_Ok() {
        List<String> expectedReport = List.of(
                "fruit,quantity"
        );
        List<String> report = reportGenerator.getReport();
        assertEquals(expectedReport, report);
    }
}
