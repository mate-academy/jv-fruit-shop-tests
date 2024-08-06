package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.ReportDao;
import core.basesyntax.dao.impl.ReportDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static final String REPORT_DAO_WITHOUT_TRANSACTION = "fruit,quantity"
            + System.lineSeparator();

    private ReportDao reportDao;
    private ReportGenerator reportGenerator;

    @BeforeEach
    public void setUp() {
        reportDao = new ReportDaoImpl();
        reportGenerator = new ReportGeneratorImpl(reportDao);
    }

    @Test
    public void generateReport_withTransactions_ok() {
        reportDao.updateReport(new FruitTransaction(Operation.PURCHASE, "banana", 152));
        reportDao.updateReport(new FruitTransaction(Operation.PURCHASE, "apple", 90));
        String actual = reportGenerator.generateReport();
        assertEquals(REPORT, actual);
    }

    @Test
    public void generateReport_withoutTransactions_ok() {
        // Generate report when no transactions exist
        String actual = reportGenerator.generateReport();
        assertEquals(REPORT_DAO_WITHOUT_TRANSACTION, actual);
    }
}
