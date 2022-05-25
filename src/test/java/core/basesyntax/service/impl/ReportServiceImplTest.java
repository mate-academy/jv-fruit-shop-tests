package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static Exception exception;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Before
    public void setUp() {
        exception = new Exception();
    }

    @Test
    public void getReport_nullList_notOK() {
        try {
            reportService.getReport(null);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(RuntimeException.class, exception.getClass());
    }

    @Test
    public void getReport_goodList_OK() {
        FruitTransaction appleTransaction = new FruitTransaction();
        appleTransaction.setFruit("apple");
        appleTransaction.setQuantity(10);
        appleTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        FruitTransaction bananaTransaction = new FruitTransaction();
        bananaTransaction.setFruit("banana");
        bananaTransaction.setQuantity(20);
        bananaTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(appleTransaction);
        fruitTransactions.add(bananaTransaction);
        String report = null;
        try {
            report = reportService.getReport(fruitTransactions);
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertSame(Exception.class, exception.getClass());
        Assert.assertEquals("fruit,quantity" + System.lineSeparator()
                + "apple,10" + System.lineSeparator()
                + "banana,20" + System.lineSeparator(), report);
    }
}
