package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.repository.StorageRepository;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String EXPECTED_REPORT = "fruit,quantity" + System.lineSeparator()
            + "orange,34" + System.lineSeparator()
            + "apple,234" + System.lineSeparator()
            + "pineapple,10";
    private StorageRepository repository = new StorageRepository();
    private ReportService reportService = new ReportServiceImpl(repository);

    @BeforeEach
    void setUp() {
        repository.getProducts().clear();
        repository.add(new Transaction(Operation.BALANCE, "apple", 234));
        repository.add(new Transaction(Operation.BALANCE, "pineapple", 10));
        repository.add(new Transaction(Operation.BALANCE, "orange", 34));
    }

    @Test
    void generateReport_transactions_ok() {
        String report = reportService.generateReport();
        assertEquals(EXPECTED_REPORT, report);
    }
}
