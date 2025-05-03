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
    private static final String BANANA_FRUIT = "banana";
    private static final String APPLE_FRUIT = "apple";
    private static final int BANANA_QUANTITY = 152;
    private static final int APPLE_QUANTITY = 90;

    private ReportDao reportDao;
    private ReportGenerator reportGenerator;

    @BeforeEach
    public void setUp() {
        reportDao = new ReportDaoImpl();
        reportGenerator = new ReportGeneratorImpl(reportDao);
    }

    @Test
    public void generateReport_withTransactions_ok() {
        reportDao.updateReport(new FruitTransaction(
                Operation.PURCHASE,
                FruitConstants.BANANA,
                BANANA_QUANTITY)
        );
        reportDao.updateReport(new FruitTransaction(
                Operation.PURCHASE,
                FruitConstants.APPLE,
                APPLE_QUANTITY)
        );
        String actual = reportGenerator.generateReport();
        assertEquals(REPORT, actual);
    }

    @Test
    public void generateReport_withoutTransactions_ok() {
        String actual = reportGenerator.generateReport();
        assertEquals(REPORT_DAO_WITHOUT_TRANSACTION, actual);
    }
}
